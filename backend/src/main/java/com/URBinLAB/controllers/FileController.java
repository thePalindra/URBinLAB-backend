package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.FileAPI;
import com.URBinLAB.domains.File;
import com.URBinLAB.services.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController implements FileAPI {

    @Autowired
    private final FileService fileService;

    public FileController (FileService fileService) {
        this.fileService = fileService;
    }

}
