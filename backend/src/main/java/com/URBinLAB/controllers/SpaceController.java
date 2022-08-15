package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.SpaceAPI;
import com.URBinLAB.services.SpaceService;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/space")
public class SpaceController implements SpaceAPI {

    @Autowired
    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }


    @Override
    public ResponseEntity<String> attachSpace(MultiValueMap<String, String> map,
                                           Long id,
                                           Long document) {
        if (this.spaceService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.spaceService.attachSpace(id, document);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> addSpace(MultiValueMap<String, String> map,
                                           Long document,
                                           String space) {
        if (this.spaceService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.spaceService.addSpace(document, space);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllFromLevel(MultiValueMap<String, String> map,
                                                  Integer level) {

        if (this.spaceService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.spaceService.getAllFromLevel(level);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> searchByName(MultiValueMap<String, String> map,
                                               String name,
                                               Integer level) {

        if (this.spaceService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.spaceService.searchByName(name, level);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getEverything(MultiValueMap<String, String> map,
                                                String name) {

        if (this.spaceService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.spaceService.getEverything(name);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getTheLevelBellow(MultiValueMap<String, String> map,
                                                    String name,
                                                    Integer level) {

        if (this.spaceService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.spaceService.getTheLevelBellow(name, level);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
