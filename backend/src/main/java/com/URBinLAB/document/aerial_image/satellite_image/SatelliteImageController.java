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
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String satellite,
                                                 String resolution) {

        if (this.satelliteImageService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.satelliteImageService.createDocument(map, name, description, provider, timeScope, link, satellite, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllSatellite(MultiValueMap<String, String> map) {
        if (this.satelliteImageService.tokenChecker(map, Feature.AUX_QUERY))
            return this.satelliteImageService.getAllSatellite();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllResolution(MultiValueMap<String, String> map) {
        if (this.satelliteImageService.tokenChecker(map, Feature.AUX_QUERY))
            return this.satelliteImageService.getAllResolution();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getById(MultiValueMap<String, String> map,
                                          Long id) {
        if (this.satelliteImageService.tokenChecker(map, Feature.AUX_QUERY))
            return this.satelliteImageService.getById(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
