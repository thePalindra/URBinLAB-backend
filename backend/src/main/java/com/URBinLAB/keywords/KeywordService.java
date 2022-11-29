package com.URBinLAB.keywords;

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

import java.util.List;

@Service
public class KeywordService {

    private KeywordRepository keywordRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();


    @Autowired
    public KeywordService(KeywordRepository keywordRepository,
                          TokenRepository tokenRepository) {
        this.keywordRepository = keywordRepository;
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
            return false;
        }
    }

    public ResponseEntity<String> addKeyword(String keyword) {
        try {
            Keyword temp = Keyword.builder()
                    .keyword(keyword)
                    .build();

            return new ResponseEntity<>(new Gson().toJson(this.keywordRepository.save(temp).getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getKeywordByKeyword(String keyword) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.keywordRepository.getKeywordByKeyword(keyword)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addKeywordToDocument(Long document, List<Long> keywords) {
        try {
            for (int i = 0; i<keywords.size(); i++)
                this.keywordRepository.addKeywordToDocument(document, keywords.get(i));

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByKeyword() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.keywordRepository.groupByKeyword()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
