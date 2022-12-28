package com.URBinLAB.document.statistics.census;

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
@RequestMapping("/census")
public class CensusController implements StatisticsAPI {

    @Autowired
    private CensusService censusService;

    public CensusController(CensusService censusService) {
        this.censusService = censusService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String theme) {
        return this.censusService.createDocument(map, name, description, provider, timeScope, link, theme);
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
