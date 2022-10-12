package com.URBinLAB.controllers;


import com.URBinLAB.controllerAPI.ReportsAPI;
import com.URBinLAB.services.ReportsService;
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
@RequestMapping("/reports")
public class ReportsController implements ReportsAPI {

    @Autowired
    private ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String context,
                                                 String theme) {

        if (this.reportsService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.reportsService.createDocument(map, name, description, provider, timeScope, link, context, theme);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllContext(MultiValueMap<String, String> map) {
        if (this.reportsService.tokenChecker(map, Feature.AUX_QUERY))
            return this.reportsService.getAllContext();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllTheme(MultiValueMap<String, String> map) {
        if (this.reportsService.tokenChecker(map, Feature.AUX_QUERY))
            return this.reportsService.getAllTheme();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
