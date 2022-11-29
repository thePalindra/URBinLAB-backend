package com.URBinLAB.document;

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
    public ResponseEntity<String> getDocumentBySpaceMarker(MultiValueMap<String, String> map,
                                                           String space,
                                                           Integer page) {

        if (this.documentService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.documentService.getDocumentBySpaceMarker(space, page);

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
                                           Integer maxYear,
                                           Integer minYear,
                                           Long archiver,
                                           String[] types,
                                           Integer page) {

        if (this.documentService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.documentService.getElementsFromQuery(name, provider, archiver, maxYear, minYear, types, page);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> fromList(MultiValueMap<String, String> map,
                                           Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getFromList(list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getSpaceFromDocument(MultiValueMap<String, String> map, Long id) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getSpaceFromDocument(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllProviders(MultiValueMap<String, String> map) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getAllProviders();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllURLs(MultiValueMap<String, String> map) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getAllURLs();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getDocumentByNameInList(MultiValueMap<String, String> map,
                                                    String name,
                                                    Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getDocumentByNameInList(name, list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> groupByProvider(MultiValueMap<String, String> map,
                                                  Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.groupByProvider(list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> groupByYear(MultiValueMap<String, String> map,
                                                  Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.groupByYear(list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> groupByType(MultiValueMap<String, String> map,
                                              Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.groupByType(list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> groupByArchiver(MultiValueMap<String, String> map,
                                                  Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.groupByArchiver(list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getDocumentByYear(MultiValueMap<String, String> map,
                                                    Integer[] years,
                                                    String[] providers,
                                                    Integer[] archivers,
                                                    String[] types,
                                                    Integer[] list) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getDocumentByYear(years, providers, archivers, types, list);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAll(MultiValueMap<String, String> map) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getAll();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getDocumentByName(MultiValueMap<String, String> map,
                                                    String name) {
        if (this.documentService.tokenChecker(map, Feature.AUX_QUERY))
            return this.documentService.getDocumentByName(name);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
