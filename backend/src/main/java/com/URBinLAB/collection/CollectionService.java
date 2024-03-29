package com.URBinLAB.collection;

import com.URBinLAB.document.Document;
import com.URBinLAB.token.Token;
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

    public ResponseEntity<String> getAllCollections() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.collectionRepository.getAllCollections()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createCollection(String token,
                                                   String name,
                                                   String description) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Collection collection = Collection.builder()
                    .name(name)
                    .description(description)
                    .archiver(temp.getResearcher())
                    .build();

            collection = this.collectionRepository.save(collection);

            return new ResponseEntity<>(new Gson().toJson(collection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
