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
    private final Gson gson = new Gson();


    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public ResponseEntity<String> addKeyword(String keyword) {
            Keyword temp = Keyword.builder()
                    .keyword(keyword)
                    .build();

            return new ResponseEntity<>(new Gson().toJson(this.keywordRepository.save(temp)), HttpStatus.OK);

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

    public ResponseEntity<String> getAll() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.keywordRepository.getAll()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
