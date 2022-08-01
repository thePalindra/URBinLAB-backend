package com.URBinLAB.services;

import com.URBinLAB.domains.*;
import com.URBinLAB.repositories.*;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public FileService(FileRepository fileRepository,
                       DocumentRepository documentRepository,
                       TokenRepository tokenRepository) {

        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
    }

    public boolean tokenChecker (MultiValueMap<String, String> map, Feature feature) {
        try {
            if (!map.containsKey("token"))
                return AccessControl.access(feature, "all");

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Token toCompare = this.tokenRepository.getById(temp.getId());
            if (!temp.getToken().equals(toCompare.getToken()))
                return false;

            if (System.currentTimeMillis() > temp.getLogin().getTime() + AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document,
                                             String name,
                                             String format,
                                             Date creation,
                                             Long size) {
        try {

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            File saved = File.builder()
                    .name(name)
                    .file(file.getBytes())
                    .document(doc.get())
                    .creationDate(creation)
                    .format(format)
                    .size(size)
                    .build();
            this.fileRepository.save(saved);
            return new ResponseEntity<>(new Gson().toJson(size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}