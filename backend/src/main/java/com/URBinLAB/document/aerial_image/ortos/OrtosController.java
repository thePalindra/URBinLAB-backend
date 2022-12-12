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
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution,
                                                 String scale) {

        if (this.ortosService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.ortosService.createDocument(map, name, description, provider, timeScope, link, scale, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllScale(MultiValueMap<String, String> map) {
        if (this.ortosService.tokenChecker(map, Feature.AUX_QUERY))
            return this.ortosService.getAllScale();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllResolution(MultiValueMap<String, String> map) {
        if (this.ortosService.tokenChecker(map, Feature.AUX_QUERY))
            return this.ortosService.getAllResolution();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getById(MultiValueMap<String, String> map,
                                          Long id) {
        if (this.ortosService.tokenChecker(map, Feature.AUX_QUERY))
            return this.ortosService.getById(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
