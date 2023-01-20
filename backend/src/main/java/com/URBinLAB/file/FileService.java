package com.URBinLAB.file;

import com.URBinLAB.document.Document;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class FileService {

    static final String VOLUME_PATH = "/data/filesystem/";

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;
    private HttpServletRequest request;
    private final Gson gson = new Gson();

    @Autowired
    public FileService(FileRepository fileRepository,
                       DocumentRepository documentRepository,
                       HttpServletRequest request) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
        this.request = request;
    }

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document) {

        try {
            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.NOT_FOUND);

            File saved = File.builder()
                    .name(file.getOriginalFilename())
                    .file(VOLUME_PATH + file.getOriginalFilename())
                    .document(doc.get())
                    .creationDate(new Date())
                    .format(file.getContentType())
                    .size(file.getSize())
                    .build();

            String finalPath = this.pathMaker(doc.get());

            System.out.println(request.getServletContext().getRealPath("/"));
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
                break;
        }
        finalPath += doc.getType() + "/";
        return finalPath;
    }

    public ResponseEntity<String> getFiles(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.fileRepository.getFiles(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
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