package com.URBinLAB.document.sensors;

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
@RequestMapping("/sensors")
public class SensorsController implements SensorsAPI {

    @Autowired
    private SensorsService sensorsService;

    public SensorsController(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String variable) {
        return this.sensorsService.createDocument(map, name, description, provider, timeScope, link, variable);
    }

    @Override
    public ResponseEntity<String> getAllVariable() {
        return this.sensorsService.getAllVariable();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.sensorsService.getById(id);
    }
}
