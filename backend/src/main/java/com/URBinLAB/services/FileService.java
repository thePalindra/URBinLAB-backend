package com.URBinLAB.services;

import com.URBinLAB.domains.Collection;
import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.File;
import com.URBinLAB.domains.Researcher;
import com.URBinLAB.repositories.CollectionRepository;
import com.URBinLAB.repositories.DocumentRepository;
import com.URBinLAB.repositories.FileRepository;

import com.URBinLAB.repositories.ResearcherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FileService {

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;
    private CollectionRepository collectionRepository;
    private ResearcherRepository researcherRepository;
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    public FileService(FileRepository fileRepository, DocumentRepository documentRepository, CollectionRepository collectionRepository, ResearcherRepository researcherRepository) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
        this.collectionRepository = collectionRepository;
        this.researcherRepository = researcherRepository;
    }

    @Transactional
    public ResponseEntity<String> addFile(File data) {
        try {
            List<File> list = this.fileRepository.checkIfExists(data.getFormat(), data.getDocument());
            if (!list.isEmpty())
                return new ResponseEntity<>(this.ow.writeValueAsString(list.get(0).getId()), HttpStatus.OK);

            Document doc = this.documentRepository.getById(data.getDocument().getId());
            //doc.addFile();
            this.documentRepository.save(doc);
            this.fileRepository.save(data);
            return new ResponseEntity<>(this.ow.writeValueAsString(data.getId()), HttpStatus.OK);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<String> attachFile(MultipartFile file, Long id) {
        try {
            File saved = this.fileRepository.getById(id);
            //saved.setFile(file.getBytes());
            this.fileRepository.save(saved);
            return new ResponseEntity<>(this.ow.writeValueAsString(saved.getId()), HttpStatus.OK);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getFiles() {
        try {
            return new ResponseEntity<>(this.ow.writeValueAsString(this.fileRepository.findAll()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getFileById(Long id) {
        try {
            return new ResponseEntity<>(ow.writeValueAsString(this.fileRepository.getById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@Transactional
    public ResponseEntity<String> addFile(Long collectionId, Long researcherId
            , String name, String description
            , String type, String provider
            , Date start, Date end
            , String spaceName, String spaceType
            , boolean flag, String format
            , Date creationDate, MultipartFile file) throws JsonProcessingException {

        try {

            Document newDoc;
            List<Document> doc = this.documentRepository.getOneByName(name);
            if (doc.isEmpty()) {
                Collection collection = this.collectionRepository.getById(collectionId);
                Researcher researcher = this.researcherRepository.getById(researcherId);
                Document temp = Document.builder()
                        .collection(collection)
                        .whoAdded(researcher)
                        .name(name)
                        .description(description)
                        .type(type)
                        .provider(provider)
                        .scopeStart(start)
                        .scopeEnd(end)
                        .spaceName(spaceName)
                        .spaceType(spaceType)
                        .flag(flag)
                        .clicks(0)
                        .files(0)
                        .build();

                newDoc = this.documentRepository.save(temp);
            } else
                newDoc = doc.get(0);

            List<File> list = this.fileRepository.checkIfExists(format, newDoc);
            if (!list.isEmpty())
                return new ResponseEntity<>(this.ow.writeValueAsString(list.get(0).getId()), HttpStatus.OK);

            File temp = File.builder()
                    .document(newDoc)
                    .format(format)
                    .creationDate(creationDate)
                    .file(file.getBytes())
                    .build();

            File res = this.fileRepository.save(temp);
            System.out.println(temp.getFile());
            return new ResponseEntity<>("this.ow.writeValueAsString(res)", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}