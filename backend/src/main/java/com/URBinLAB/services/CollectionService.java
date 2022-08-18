package com.URBinLAB.services;

import com.URBinLAB.domains.Collection;
import com.URBinLAB.domains.Token;
import com.URBinLAB.repositories.CollectionRepository;
import com.URBinLAB.repositories.ResearcherRepository;
import com.URBinLAB.repositories.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public CollectionService(CollectionRepository collectionRepository,
                             TokenRepository tokenRepository) {
        this.collectionRepository = collectionRepository;
        this.tokenRepository = tokenRepository;
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
            System.out.println("OH OH");
            return false;
        }
    }

    public ResponseEntity<String> createCollection(Collection data) {
        try {
            Collection collection = this.collectionRepository.save(data);
            return new ResponseEntity<>(this.gson.toJson(collection.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getCollectionById(Long id) {
        try {
            Collection collection = this.collectionRepository.getById(id);
            if (collection == null)
                return new ResponseEntity<>("No collection!", HttpStatus.OK);
            return new ResponseEntity<>(this.gson.toJson(collection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateCollection(Collection data) {
        try {
            Collection collection = this.collectionRepository.getById(data.getId());
            collection = data;
            this.collectionRepository.save(collection);
            return new ResponseEntity<>(this.gson.toJson(collection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllCollections() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.collectionRepository.getAllCollections()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
}
