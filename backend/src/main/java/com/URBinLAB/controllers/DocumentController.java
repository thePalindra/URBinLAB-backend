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
import org.springframework.web.multipart.MultipartFile;

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
                                                 Long collectionId,
                                                 String name,
                                                 String description,
                                                 String type,
                                                 String provider,
                                                 Date timeScope,
                                                 String link) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.addDocument(map, collectionId, name, description, type, provider, timeScope, link);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> createAerialImage(MultiValueMap<String, String> map, Long collectionId, String name, String description, String type, String provider, Date timeScope, String link, Integer scale) {
        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.createAerialImage(map, collectionId, name, description, type, provider, timeScope, link, scale);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> addFile(MultiValueMap<String, String> map,
                                          MultipartFile file,
                                          Long document,
                                          String name,
                                          String format,
                                          Date creation,
                                          Long size) {

        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.attachFile(file, document, name, format, creation, size);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> addSpace(MultiValueMap<String, String> map,
                                           Long id,
                                           Long document) {
        if (this.documentService.tokenChecker(map, Feature.ADDDOCUMENT))
            return this.documentService.setSpace(id, document);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

}
