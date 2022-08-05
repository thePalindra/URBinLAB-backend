package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.PhotographyAPI;
import com.URBinLAB.services.PhotographyService;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/photography")
public class PhotographyController implements PhotographyAPI {

    @Autowired
    private PhotographyService photographyService;

    public PhotographyController(PhotographyService photographyService) {
        this.photographyService = photographyService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution,
                                                 Boolean color) {

        if (this.photographyService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.photographyService.createDocument(map, name, description, provider, timeScope, link, resolution, color);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
