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
    public ResponseEntity<String> createDocument(String map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link) {
        return this.documentService.createDocument(map, name, description, provider, timeScope, link);
    }

    @Override
    public ResponseEntity<String> deleteDocument(Long id) {
        return this.documentService.deleteDocument(id);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceGeometry(String space) {
        return this.documentService.getDocumentBySpaceGeometry(space);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceMarker(String space) {
        return this.documentService.getDocumentBySpaceMarker(space);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceCircle(Double lng,
                                                           Double lat,
                                                           Double size) {
        return this.documentService.getDocumentBySpaceCircle(lng, lat, size);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceGeometry(String space, Integer[] list) {
        return this.documentService.getDocumentBySpaceGeometry(space, list);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceMarker(String space, Integer[] list) {
        return this.documentService.getDocumentBySpaceMarker(space, list);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceCircle(Double lng, Double lat, Double size, Integer[] list) {
        return this.documentService.getDocumentBySpaceCircle(lng, lat, size, list);
    }

    @Override
    public ResponseEntity<String> bigQuery(String name,
                                           String provider,
                                           Integer maxYear,
                                           Integer minYear,
                                           Long archiver,
                                           String[] types,
                                           Integer page) {
        return this.documentService.getElementsFromQuery(name, provider, archiver, maxYear, minYear, types, page);
    }

    @Override
    public ResponseEntity<String> fromList(Integer[] list) {
        return this.documentService.getFromList(list);
    }

    @Override
    public ResponseEntity<String> getSpaceFromDocument(Long id) {
        return this.documentService.getSpaceFromDocument(id);
    }

    @Override
    public ResponseEntity<String> getAllProviders() {
        return this.documentService.getAllProviders();
    }

    @Override
    public ResponseEntity<String> getAllURLs() {
        return this.documentService.getAllURLs();
    }

    @Override
    public ResponseEntity<String> getDocumentByName(String name,
                                                    Integer[] list) {
        return this.documentService.getDocumentByName(name, list);
    }

    @Override
    public ResponseEntity<String> groupByProvider(Integer[] list) {
        return this.documentService.groupByProvider(list);
    }

    @Override
    public ResponseEntity<String> groupByYear(Integer[] list) {
        return this.documentService.groupByYear(list);
    }

    @Override
    public ResponseEntity<String> groupByType(Integer[] list) {
        return this.documentService.groupByType(list);
    }

    @Override
    public ResponseEntity<String> groupByArchiver(Integer[] list) {
        return this.documentService.groupByArchiver(list);
    }

    @Override
    public ResponseEntity<String> groupByProvider() {
        return this.documentService.groupByProvider();
    }

    @Override
    public ResponseEntity<String> groupByYear() {
        return this.documentService.groupByYear();
    }

    @Override
    public ResponseEntity<String> groupByType() {
        return this.documentService.groupByType();
    }

    @Override
    public ResponseEntity<String> groupByArchiver() {
        return this.documentService.groupByArchiver();
    }

    @Override
    public ResponseEntity<String> filter(Integer[] years,
                                         String[] providers,
                                         Integer[] archivers,
                                         String[] types,
                                         Integer[] list) {
        return this.documentService.filter(years, providers, archivers, types, list);
    }

    @Override
    public ResponseEntity<String> filter(Integer[] years,
                                         String[] providers,
                                         Integer[] archivers,
                                         String[] types) {
        return this.documentService.filter(years, providers, archivers, types);
    }

    @Override
    public ResponseEntity<String> getAll(Long limit) {
        return this.documentService.getAll(limit);
    }

    @Override
    public ResponseEntity<String> getDocumentByName(String name) {
        return this.documentService.getDocumentByName(name);
    }

    @Override
    public ResponseEntity<String> orderByYearAsc(Integer[] list) {
        return this.documentService.orderByYearAsc(list);
    }

    @Override
    public ResponseEntity<String> orderByYearDesc(Integer[] list) {
        return this.documentService.orderByYearDesc(list);
    }

    @Override
    public ResponseEntity<String> orderByNameAsc(Integer[] list) {
        return this.documentService.orderByNameAsc(list);
    }

    @Override
    public ResponseEntity<String> orderByNameDesc(Integer[] list) {
        return this.documentService.orderByNameDesc(list);
    }

    @Override
    public ResponseEntity<String> getDocumentById(Long id) {
        return this.documentService.getDocumentById(id);
    }

    @Override
    public ResponseEntity<String> addCollection(Long id,
                                                Long collection) {
        return this.documentService.addCollection(id, collection);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceId(Long id) {
        return this.documentService.getDocumentBySpaceId(id);
    }

    @Override
    public ResponseEntity<String> getDocumentBySpaceId(Long id, Integer[] list) {
        return this.documentService.getDocumentBySpaceId(id, list);
    }
}
