package com.URBinLAB.controllers;


import com.URBinLAB.controllerAPI.CollectionAPI;
import com.URBinLAB.domains.Collection;
import com.URBinLAB.services.CollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/collection")
public class CollectionController implements CollectionAPI {

    @Autowired
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

}
