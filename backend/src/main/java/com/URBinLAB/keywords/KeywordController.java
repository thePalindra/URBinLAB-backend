package com.URBinLAB.keywords;

import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/keyword")
public class KeywordController implements KeywordAPI {

    @Autowired
    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Override
    public ResponseEntity<String> addKeyword(String keyword) {
        return this.keywordService.addKeyword(keyword);
    }

    @Override
    public ResponseEntity<String> getKeywordByKeyword(String keyword) {
        return this.keywordService.getKeywordByKeyword(keyword);
    }

    @Override
    public ResponseEntity<String> getAll() {
        return this.keywordService.getAll();
    }

    @Override
    public ResponseEntity<String> addKeywordToDocument(Long document,
                                                       List<Long> keywords) {
        return this.keywordService.addKeywordToDocument(document, keywords);
    }

    @Override
    public ResponseEntity<String> groupByKeyword() {
        return this.keywordService.groupByKeyword();
    }
}
