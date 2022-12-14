package com.URBinLAB.file;

import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                                          Long document) throws IOException {

        if (this.fileService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.fileService.attachFile(file, document);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getFiles(MultiValueMap<String, String> map,
                                           Long id) {

        if (this.fileService.tokenChecker(map, Feature.AUX_QUERY))
            return this.fileService.getFiles(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> deleteFile(MultiValueMap<String, String> map,
                                             Long id) {

        if (this.fileService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.fileService.deleteFile(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
