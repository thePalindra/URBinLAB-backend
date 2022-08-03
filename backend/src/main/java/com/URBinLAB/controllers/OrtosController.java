package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.OrtosAPI;
import com.URBinLAB.services.OrtosService;
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
@RequestMapping("/ortos")
public class OrtosController implements OrtosAPI {

    @Autowired
    private OrtosService ortosService;

    public OrtosController(OrtosService ortosService) {
        this.ortosService = ortosService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution,
                                                 Integer scale) {

        if (this.ortosService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.ortosService.createDocument(map, name, description, provider, timeScope, link, scale, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
