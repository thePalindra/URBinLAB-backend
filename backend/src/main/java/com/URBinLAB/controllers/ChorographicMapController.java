package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.ChorographicMapAPI;
import com.URBinLAB.services.ChorographicMapService;
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
@RequestMapping("/chorographic_map")
public class ChorographicMapController implements ChorographicMapAPI {

    @Autowired
    private ChorographicMapService chorographicMapService;

    public ChorographicMapController(ChorographicMapService chorographicMapService) {
        this.chorographicMapService = chorographicMapService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 Integer scale,
                                                 Boolean raster,
                                                 String resolution,
                                                 String type) {

        if (this.chorographicMapService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.chorographicMapService.createDocument(map, name, description, provider, timeScope, link, scale, raster, resolution, type);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
