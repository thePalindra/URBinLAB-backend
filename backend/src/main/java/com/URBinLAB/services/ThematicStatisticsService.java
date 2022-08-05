package com.URBinLAB.services;

import com.URBinLAB.domains.*;
import com.URBinLAB.repositories.*;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;

@Service
public class ThematicStatisticsService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private StatisticsRepository statisticsRepository;
    private ThematicStatisticsRepository thematicStatisticsRepository;
    private final Gson gson = new Gson();

    public ThematicStatisticsService(DocumentRepository documentRepository,
                                     TokenRepository tokenRepository,
                                     StatisticsRepository statisticsRepository,
                                     ThematicStatisticsRepository thematicStatisticsRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.statisticsRepository = statisticsRepository;
        this.thematicStatisticsRepository = thematicStatisticsRepository;
    }

    public boolean tokenChecker (MultiValueMap<String, String> map, Feature feature) {
        try {
            if (!map.containsKey("token"))
                return AccessControl.access(feature, "all");

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Token toCompare = this.tokenRepository.getById(temp.getId());
            if (!temp.getToken().equals(toCompare.getToken()))
                return false;

            if (System.currentTimeMillis() > temp.getLogin().getTime() + AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String theme) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("THEMATICSTATISTICS")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Statistics statistics = Statistics.builder()
                    .document(document)
                    .theme(theme)
                    .build();

            statistics = this.statisticsRepository.save(statistics);

            ThematicStatistics thematicStatistics = ThematicStatistics.builder()
                    .statistics(statistics)
                    .build();

            thematicStatistics = this.thematicStatisticsRepository.save(thematicStatistics);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
