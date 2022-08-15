package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.AerialPhotographyAPI;
import com.URBinLAB.services.AerialPhotographyService;
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
@RequestMapping("/aerial_photography")
public class AerialPhotographyController implements AerialPhotographyAPI {

    @Autowired
    private AerialPhotographyService aerialPhotographyService;

    public AerialPhotographyController(AerialPhotographyService aerialPhotographyService) {
        this.aerialPhotographyService = aerialPhotographyService;
    }

    @Override
    public ResponseEntity<String> createAerialPhotography(MultiValueMap<String, String> map,
                                                          String name,
                                                          String description,
                                                          String provider,
                                                          Date timeScope,
                                                          String link,
                                                          Integer scale,
                                                          String resolution) {
        if (this.aerialPhotographyService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.aerialPhotographyService.createAerialPhotography(map, name, description, provider, timeScope, link, scale, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
