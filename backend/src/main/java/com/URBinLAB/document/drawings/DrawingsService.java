package com.URBinLAB.document.drawings;

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
public class DrawingsService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private DrawingsRepository drawingsRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DrawingsService(DocumentRepository documentRepository,
                           TokenRepository tokenRepository,
                           DrawingsRepository drawingsRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.drawingsRepository = drawingsRepository;
    }

    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String context) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("DRAWINGS")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Drawings drawings = Drawings.builder()
                    .context(context)
                    .document(document)
                    .build();

            drawings = this.drawingsRepository.save(drawings);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllContext() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.drawingsRepository.getAllContext()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.drawingsRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
