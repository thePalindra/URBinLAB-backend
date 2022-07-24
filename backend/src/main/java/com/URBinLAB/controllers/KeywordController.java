package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.KeywordAPI;
import com.URBinLAB.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/keyword")
public class KeywordController implements KeywordAPI {

    @Autowired
    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }
}
