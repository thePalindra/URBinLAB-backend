package com.URBinLAB.document.photography;

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
public class PhotographyService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private PhotographyRepository photographyRepository;
    private final Gson gson = new Gson();

    @Autowired
    public PhotographyService(DocumentRepository documentRepository,
                              TokenRepository tokenRepository,
                              PhotographyRepository photographyRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.photographyRepository = photographyRepository;
    }

    public ResponseEntity<String> createDocument(String token,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution,
                                                 Boolean color) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("PHOTOS")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Photography photography = Photography.builder()
                    .document(document)
                    .resolution(resolution)
                    .colors(color)
                    .build();

            photography = this.photographyRepository.save(photography);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllImageResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.photographyRepository.getAllImageResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.photographyRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
