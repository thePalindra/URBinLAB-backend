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
    public ResponseEntity<String> addKeyword(MultiValueMap<String, String> map,
                                             String keyword) {

        if (this.keywordService.tokenChecker(map, Feature.ADD_KEYWORD))
            return this.keywordService.addKeyword(keyword);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getKeywordByKeyword(MultiValueMap<String, String> map,
                                                      String keyword) {

        if (this.keywordService.tokenChecker(map, Feature.AUX_QUERY))
            return this.keywordService.getKeywordByKeyword(keyword);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> addKeywordToDocument(MultiValueMap<String, String> map,
                                                       Long document,
                                                       List<Long> keywords) {

        if (this.keywordService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.keywordService.addKeywordToDocument(document, keywords);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
