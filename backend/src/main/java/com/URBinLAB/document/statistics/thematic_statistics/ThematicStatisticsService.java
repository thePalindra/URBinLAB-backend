package com.URBinLAB.document.statistics.thematic_statistics;

import com.URBinLAB.document.Document;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.document.statistics.StatisticsRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;
import com.URBinLAB.document.statistics.Statistics;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
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

    public ResponseEntity<String> createDocument(String token,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String theme) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Long id = this.documentRepository.getMaxId();

            if (id == null)
                id = 0l;

            System.out.println(id.toString());

            Document document = Document.builder()
                    .id(id+1)
                    .archiver(temp.getResearcher())
                    .type("THEMATIC STATISTICS")
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

    public ResponseEntity<String> getAllThemes() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.statisticsRepository.getAllThemes()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.statisticsRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
