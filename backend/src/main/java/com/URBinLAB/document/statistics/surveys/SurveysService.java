package com.URBinLAB.document.statistics.surveys;

import com.URBinLAB.document.Document;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.document.statistics.StatisticsRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;
import com.URBinLAB.document.statistics.Statistics;
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
public class SurveysService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private StatisticsRepository statisticsRepository;
    private SurveysRepository surveysRepository;
    private final Gson gson = new Gson();

    @Autowired
    public SurveysService(DocumentRepository documentRepository,
                          TokenRepository tokenRepository,
                          StatisticsRepository statisticsRepository,
                          SurveysRepository surveysRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.statisticsRepository = statisticsRepository;
        this.surveysRepository = surveysRepository;
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
                    .type("SURVEYS")
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

            Surveys surveys = Surveys.builder()
                    .statistics(statistics)
                    .build();

            surveys = this.surveysRepository.save(surveys);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
