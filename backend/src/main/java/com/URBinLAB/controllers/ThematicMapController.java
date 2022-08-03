package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.ThematicMapAPI;
import com.URBinLAB.services.ThematicMapService;
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
@RequestMapping("/thematic_map")
public class ThematicMapController implements ThematicMapAPI {

    @Autowired
    private ThematicMapService thematicMapService;

    public ThematicMapController(ThematicMapService thematicMapService) {
        this.thematicMapService = thematicMapService;
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
                                                 String type,
                                                 String theme,
                                                 String mapType) {

        if (this.thematicMapService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.thematicMapService.createDocument(map, name, description, provider, timeScope, link, scale, raster, resolution, type, theme, mapType);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
