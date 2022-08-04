package com.URBinLAB.services;

import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.Drawings;
import com.URBinLAB.domains.Photography;
import com.URBinLAB.domains.Token;
import com.URBinLAB.repositories.DocumentRepository;
import com.URBinLAB.repositories.DrawingsRepository;
import com.URBinLAB.repositories.PhotographyRepository;
import com.URBinLAB.repositories.TokenRepository;
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

            return new ResponseEntity<>(new Gson().toJson(drawings), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}