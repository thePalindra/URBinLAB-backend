package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.DrawingsAPI;
import com.URBinLAB.services.DrawingsService;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Date;

public class DrawingsController implements DrawingsAPI {

    @Autowired
    private DrawingsService drawingsService;

    public DrawingsController(DrawingsService drawingsService) {
        this.drawingsService = drawingsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String context) {

        if (this.drawingsService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.drawingsService.createDocument(map, name, description, provider, timeScope, link, context);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
