package com.URBinLAB.file;

import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<String> addFile(MultipartFile file,
                                          Long document) throws IOException {
        return this.fileService.attachFile(file, document);
    }

    @Override
    public ResponseEntity<String> getFiles(Long id) {
        return this.fileService.getFiles(id);
    }

    @Override
    public ResponseEntity<String> deleteFile(Long id) {
        return this.fileService.deleteFile(id);
    }

    @Override
    public ResponseEntity<String> updateFile(Long id,
                                             Long document) {
        return this.fileService.updateFile(id, document);
    }

    @Override
    public ResponseEntity<Resource> getFilesToDownload(Long id) {
        return this.fileService.getFilesToDownload(id);
    }
}
