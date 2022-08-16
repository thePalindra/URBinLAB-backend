package com.URBinLAB.services;

import com.URBinLAB.domains.*;
import com.URBinLAB.repositories.*;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.codec.Wkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private SpaceRepository spaceRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           TokenRepository tokenRepository,
                           SpaceRepository spaceRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.spaceRepository = spaceRepository;
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
                                                 String link) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("GENERIC")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getDocumentBySpaceId(Long id, Integer page) {

        Pageable element = PageRequest.of(page, 10);

        List<List<Object>> list = new ArrayList<>();
        list.add(this.documentRepository.getDocumentBySpaceId(element,id));
        list.add(this.spaceRepository.getAllTheDocuments(element, id));

        return new ResponseEntity<>(new Gson().toJson(list), HttpStatus.OK);
    }

    public ResponseEntity<String> getDocumentBySpaceGeometry(String space, Integer page) {

        System.out.println(space);

        Pageable element = PageRequest.of(page, 10000);

        return new ResponseEntity<>(new Gson().toJson(this.spaceRepository.getAllTheDocumentsByGeometry(element, space)), HttpStatus.OK);
    }
}