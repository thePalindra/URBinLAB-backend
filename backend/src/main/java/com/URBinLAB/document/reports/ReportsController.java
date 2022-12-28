package com.URBinLAB.document.reports;


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
@RequestMapping("/reports")
public class ReportsController implements ReportsAPI {

    @Autowired
    private ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String context,
                                                 String theme) {

        return this.reportsService.createDocument(map, name, description, provider, timeScope, link, context, theme);
    }

    @Override
    public ResponseEntity<String> getAllContext() {
        return this.reportsService.getAllContext();

    }

    @Override
    public ResponseEntity<String> getAllTheme() {
        return this.reportsService.getAllTheme();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.reportsService.getById(id);
    }
}
