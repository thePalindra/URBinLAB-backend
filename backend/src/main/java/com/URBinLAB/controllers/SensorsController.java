package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.SensorsAPI;
import com.URBinLAB.services.SensorsService;
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
@RequestMapping("/sensors")
public class SensorsController implements SensorsAPI {

    @Autowired
    private SensorsService sensorsService;

    public SensorsController(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String variable) {

        if (this.sensorsService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.sensorsService.createDocument(map, name, description, provider, timeScope, link, variable);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllVariable(MultiValueMap<String, String> map) {
        if (this.sensorsService.tokenChecker(map, Feature.AUX_QUERY))
            return this.sensorsService.getAllVariable();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
