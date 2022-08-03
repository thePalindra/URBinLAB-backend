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
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
import java.util.Optional;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private PhotographyRepository photographyRepository;
    private CartographyRepository cartographyRepository;
    private ThematicMapRepository thematicMapRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           TokenRepository tokenRepository,
                           PhotographyRepository photographyRepository,
                           CartographyRepository cartographyRepository,
                           ThematicMapRepository thematicMapRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.photographyRepository = photographyRepository;
        this.cartographyRepository = cartographyRepository;
        this.thematicMapRepository = thematicMapRepository;
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

    public ResponseEntity<String> addDocument(MultiValueMap<String,
                                              String> map,
                                              String name,
                                              String description,
                                              String type,
                                              String provider,
                                              Date timeScope,
                                              String link) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            return new ResponseEntity<>(new Gson().toJson(document), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createPhotography(MultiValueMap<String,
                                                    String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type(type)
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
                    .build();

            photography = this.photographyRepository.save(photography);

            return new ResponseEntity<>(new Gson().toJson(photography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createCartography(MultiValueMap<String,
                                                    String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String format) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Cartography cartography = Cartography.builder()
                    .document(document)
                    .scale(scale)
                    .format(format)
                    .build();

            cartography = this.cartographyRepository.save(cartography);

            return new ResponseEntity<>(new Gson().toJson(cartography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createThematicMap(MultiValueMap<String,
                                                    String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String format,
                                                    String theme) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Cartography cartography = Cartography.builder()
                    .document(document)
                    .scale(scale)
                    .format(format)
                    .build();

            cartography = this.cartographyRepository.save(cartography);

            ThematicMap thematicMap = ThematicMap.builder()
                    .cartography(cartography)
                    .theme(theme)
                    .build();

            thematicMap = this.thematicMapRepository.save(thematicMap);

            return new ResponseEntity<>(new Gson().toJson(thematicMap), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}