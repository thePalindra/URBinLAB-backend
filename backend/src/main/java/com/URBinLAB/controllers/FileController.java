package com.URBinLAB.controllers;

import com.URBinLAB.controllerAPI.FileAPI;
import com.URBinLAB.services.FileService;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController implements FileAPI {

    @Autowired
    private final FileService fileService;

    public FileController (FileService fileService) {
        this.fileService = fileService;
    }


    @Override
    public ResponseEntity<String> addFile(MultiValueMap<String, String> map,
                                          MultipartFile file,
                                          Long document) {

        if (this.fileService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.fileService.attachFile(file, document);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
