package com.URBinLAB.document.statistics.surveys;

import com.URBinLAB.document.statistics.StatisticsAPI;
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
@RequestMapping("/surveys")
public class SurveysController implements StatisticsAPI {

    @Autowired
    private SurveysService surveysService;

    public SurveysController(SurveysService surveysService) {
        this.surveysService = surveysService;
    }

    @Override
    public ResponseEntity<String> createDocument(String map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String theme) {

        return this.surveysService.createDocument(map, name, description, provider, timeScope, link, theme);
    }

    @Override
    public ResponseEntity<String> getAllThemes() {
        return null;
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return null;
    }
}
