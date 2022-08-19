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
import java.util.Set;


@CrossOrigin
@RestController
@RequestMapping("/generic")
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
                                                 String provider,
                                                 Date timeScope,
                                                 String link) {

        if (this.documentService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.documentService.createDocument(map, name, description, provider, timeScope, link);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceGeometry(MultiValueMap<String, String> map,
                                                             String space,
                                                             Integer page) {

        if (this.documentService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.documentService.getDocumentBySpaceGeometry(space, page);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceCircle(MultiValueMap<String, String> map,
                                                           Double lng,
                                                           Double lat,
                                                           Double size,
                                                           Integer page) {

        if (this.documentService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.documentService.getDocumentBySpaceCircle(lng, lat, size, page);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> bigQuery(MultiValueMap<String, String> map,
                                           String name,
                                           String provider,
                                           Long archiver,
                                           Long maxYear,
                                           Long minYear,
                                           Set<String> types,
                                           Integer page) {

        if (this.documentService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.documentService.getElementsFromQuery(name, provider, archiver, maxYear, minYear, types, page);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
