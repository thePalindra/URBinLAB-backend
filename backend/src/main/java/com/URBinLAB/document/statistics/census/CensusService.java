package com.URBinLAB.document.statistics.census;

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
public class CensusService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private StatisticsRepository statisticsRepository;
    private CensusRepository censusRepository;
    private final Gson gson = new Gson();

    public CensusService(DocumentRepository documentRepository,
                         TokenRepository tokenRepository,
                         StatisticsRepository statisticsRepository,
                         CensusRepository censusRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.statisticsRepository = statisticsRepository;
        this.censusRepository = censusRepository;
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

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("CENSUS")
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

            Census census = Census.builder()
                    .statistics(statistics)
                    .build();

            census = this.censusRepository.save(census);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
