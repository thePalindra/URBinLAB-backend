package com.URBinLAB.document.aerial_image.ortos;

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
@RequestMapping("/ortos")
public class OrtosController implements OrtosAPI {

    @Autowired
    private OrtosService ortosService;

    public OrtosController(OrtosService ortosService) {
        this.ortosService = ortosService;
    }

    @Override
    public ResponseEntity<String> createDocument(String map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution,
                                                 String scale) {
        return this.ortosService.createDocument(map, name, description, provider, timeScope, link, scale, resolution);
    }

    @Override
    public ResponseEntity<String> getAllScale() {
        return this.ortosService.getAllScale();
    }

    @Override
    public ResponseEntity<String> getAllResolution() {
        return this.ortosService.getAllResolution();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.ortosService.getById(id);
    }
}
