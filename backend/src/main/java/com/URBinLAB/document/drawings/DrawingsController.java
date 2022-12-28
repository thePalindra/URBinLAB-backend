package com.URBinLAB.document.drawings;

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
@RequestMapping("/drawings")
public class DrawingsController implements DrawingsAPI {

    @Autowired
    private DrawingsService drawingsService;

    public DrawingsController(DrawingsService drawingsService) {
        this.drawingsService = drawingsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String context) {
        return this.drawingsService.createDocument(map, name, description, provider, timeScope, link, context);
    }

    @Override
    public ResponseEntity<String> getAllContext() {
        return this.drawingsService.getAllContext();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.drawingsService.getById(id);
    }
}
