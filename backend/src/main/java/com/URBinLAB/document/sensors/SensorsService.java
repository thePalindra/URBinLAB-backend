package com.URBinLAB.document.sensors;

import com.URBinLAB.document.Document;
import com.URBinLAB.token.Token;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.TokenRepository;
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
public class SensorsService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private SensorsRepository sensorsRepository;
    private final Gson gson = new Gson();

    @Autowired
    public SensorsService(DocumentRepository documentRepository,
                          TokenRepository tokenRepository,
                          SensorsRepository sensorsRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public ResponseEntity<String> createDocument(String token,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String variable) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("SENSORS")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Sensors sensors = Sensors.builder()
                    .variable(variable)
                    .document(document)
                    .build();

            sensors = this.sensorsRepository.save(sensors);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllVariable() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.sensorsRepository.getAllVariable()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.sensorsRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
