package com.URBinLAB.file;

import com.URBinLAB.document.Document;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    static final String VOLUME_PATH = "/data/filesystem/";

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;
    private final Gson gson = new Gson();

    @Autowired
    public FileService(FileRepository fileRepository,
                       DocumentRepository documentRepository) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
    }

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document) {

        try {
            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.NOT_FOUND);

            String finalPath = this.pathMaker(doc.get());

            File saved = File.builder()
                    .name(file.getOriginalFilename())
                    .file(finalPath)
                    .document(doc.get())
                    .creationDate(new Date())
                    .format(file.getContentType())
                    .size(file.getSize())
                    .build();


            java.io.File directory = new java.io.File(finalPath);
            if (! directory.exists())
                directory.mkdirs();

            file.transferTo(new java.io.File(finalPath + file.getOriginalFilename()));
            this.fileRepository.save(saved);
            return new ResponseEntity<>(new Gson().toJson(file), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    private String pathMaker(Document doc) {
        String finalPath = VOLUME_PATH;
        switch (doc.getType()) {
            case "THEMATIC STATISTICS":
            case "SURVEYS":
            case "CENSUS":
                finalPath += "STATISTICS/";
                break;
            case "THEMATIC MAPS":
                finalPath += "CARTOGRAPHY/THEMATIC MAPS/";
                break;
            case "GEOGRAPHIC MAPS":
            case "CHOROGRAPHIC MAPS":
            case "TOPOGRAPHIC MAPS":
            case "TOPOGRAPHIC PLANS":
                finalPath += "CARTOGRAPHY/BASE MAPS/";
                break;
            case "AERIAL PHOTOS":
            case "LiDAR":
            case "ORTOS":
            case "SATELLITE IMAGES":
                finalPath += "AERIAL IMAGES/";
                break;
            default:
                finalPath += "GENERIC/";
                break;
        }
        finalPath += doc.getType() + "/" + String.valueOf(LocalDate.parse(doc.getTimeScope().toString()).getYear()) + "/" + doc.getId().toString() + " - " + doc.getName() + "/";
        return finalPath;
    }

    public ResponseEntity<String> getFiles(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.fileRepository.getFiles(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Resource> getFilesToDownload(Long id) {
        try{
           Object path = this.fileRepository.getFilePath(id);

            java.io.File file = new java.io.File((String) path);
            InputStream fileStream = new FileInputStream(file);
            Resource resource = new InputStreamResource(fileStream);

            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteFile(Long id) {
        try{
            this.fileRepository.deleteById(id);
            return new ResponseEntity<>(new Gson().toJson("Deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateFile(Long id,
                                             Long document) {
        try {
            Document doc = this.documentRepository.getById(document);
            File file = this.fileRepository.getById(id);

            file.setDocument(doc);
            this.fileRepository.save(file);
            return new ResponseEntity<>(new Gson().toJson(file.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);

        }
    }
}