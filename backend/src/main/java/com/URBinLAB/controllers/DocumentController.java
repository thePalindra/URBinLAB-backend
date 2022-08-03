package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.DocumentAPI;

import com.URBinLAB.services.DocumentService;

import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@CrossOrigin
@RestController
@RequestMapping("/document")
public class DocumentController implements DocumentAPI {

    @Autowired
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String type,
                                                 String provider,
                                                 Date timeScope,
                                                 String link) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.createDocument(map, name, description, type, provider, timeScope, link);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> createPhotography(MultiValueMap<String, String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    String resolution) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.createPhotography(map, name, description, type, provider, timeScope, link, resolution);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> createCartography(MultiValueMap<String, String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String format) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.createCartography(map, name, description, type, provider, timeScope, link, scale, format);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> createThematicMap(MultiValueMap<String, String> map,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String format,
                                                    String theme) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.createThematicMap(map, name, description, type, provider, timeScope, link, scale, format, theme);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

}
