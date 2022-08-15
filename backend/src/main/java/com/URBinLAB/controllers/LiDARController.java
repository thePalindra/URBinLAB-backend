package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.LiDARAPI;
import com.URBinLAB.services.LiDARService;
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
@RequestMapping("/LiDAR")
public class LiDARController implements LiDARAPI {

    @Autowired
    private LiDARService liDARService;

    public LiDARController(LiDARService liDARService) {
        this.liDARService = liDARService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution) {

        if (this.liDARService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.liDARService.createDocument(map, name, description, provider, timeScope, link, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
