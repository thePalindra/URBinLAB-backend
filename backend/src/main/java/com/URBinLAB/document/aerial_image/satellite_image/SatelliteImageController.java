package com.URBinLAB.document.aerial_image.satellite_image;

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
@RequestMapping("/satellite_image")
public class SatelliteImageController implements SatelliteImageAPI {

    @Autowired
    private SatelliteImageService satelliteImageService;

    public SatelliteImageController(SatelliteImageService satelliteImageService) {
        this.satelliteImageService = satelliteImageService;
    }

    @Override
    public ResponseEntity<String> createDocument(String map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String satellite,
                                                 String resolution) {
        return this.satelliteImageService.createDocument(map, name, description, provider, timeScope, link, satellite, resolution);
    }

    @Override
    public ResponseEntity<String> getAllSatellite() {
        return this.satelliteImageService.getAllSatellite();
    }

    @Override
    public ResponseEntity<String> getAllResolution() {
        return this.satelliteImageService.getAllResolution();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.satelliteImageService.getById(id);
    }
}
