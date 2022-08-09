package com.URBinLAB.services;

import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.Space;
import com.URBinLAB.domains.Token;
import com.URBinLAB.repositories.DocumentRepository;
import com.URBinLAB.repositories.SpaceRepository;
import com.URBinLAB.repositories.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    private SpaceRepository spaceRepository;
    private TokenRepository tokenRepository;
    private DocumentRepository documentRepository;
    private final Gson gson = new Gson();

    @Autowired
    public SpaceService(SpaceRepository spaceRepository,
                        TokenRepository tokenRepository,
                        DocumentRepository documentRepository) {

        this.spaceRepository = spaceRepository;
        this.tokenRepository = tokenRepository;
        this.documentRepository = documentRepository;
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

    public ResponseEntity<String> attachSpace(Long id,
                                              Long document) {
        try {

            Optional<Space> space = this.spaceRepository.findById(id);
            if (space.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No space found!"), HttpStatus.BAD_REQUEST);

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            doc.get().setSpace(space.get());
            this.documentRepository.save(doc.get());

            return new ResponseEntity<>(new Gson().toJson(space.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllFromLevel(Integer level) {
        try {
            List<Object> spaces = this.spaceRepository.getAllFromLevel(level);

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
