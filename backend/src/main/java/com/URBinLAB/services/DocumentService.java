package com.URBinLAB.services;

import com.URBinLAB.domains.Collection;
import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.File;
import com.URBinLAB.domains.Token;
import com.URBinLAB.repositories.*;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private CollectionRepository collectionRepository;
    private FileRepository fileRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DocumentService(DocumentRepository documentRepository, CollectionRepository collectionRepository, FileRepository fileRepository,TokenRepository tokenRepository) {
        this.documentRepository = documentRepository;
        this.collectionRepository = collectionRepository;
        this.fileRepository = fileRepository;
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
            if (temp.equals(toCompare))
                return false;

            if (System.currentTimeMillis() > temp.getLogin().getTime() * AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> addDocument(MultiValueMap<String,
                                              String> map,
                                              Long collectionId,
                                              String name,
                                              String description,
                                              String type,
                                              String provider,
                                              Date timeScope,
                                              String link) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Collection collection = this.collectionRepository.getById(collectionId);
            if (collection == null)
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection)
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .build();

            document = this.documentRepository.save(document);

            return new ResponseEntity<>(new Gson().toJson(document), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document,
                                             String name,
                                             String format,
                                             Date creation,
                                             Long size) {
        try {

            Document doc = this.documentRepository.getById(document);

            File saved = File.builder()
                    .name(name)
                    .file(file.getBytes())
                    .document(doc)
                    .creationDate(creation)
                    .format(format)
                    .size(size)
                    .build();
            this.fileRepository.save(saved);
            return new ResponseEntity<>(new Gson().toJson(size), HttpStatus.OK);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}