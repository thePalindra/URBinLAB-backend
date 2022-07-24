package com.URBinLAB.services;

import com.URBinLAB.domains.Collection;
import com.URBinLAB.repositories.CollectionRepository;
import com.URBinLAB.repositories.ResearcherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;
    private ResearcherRepository researcherRepository;
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, ResearcherRepository researcherRepository) {
        this.researcherRepository = researcherRepository;
        this.collectionRepository = collectionRepository;
    }

    public ResponseEntity<String> createCollection(Collection data) {
        try {
            Collection collection = this.collectionRepository.save(data);
            return new ResponseEntity<>(this.ow.writeValueAsString(collection.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getCollectionById(Long id) {
        try {
            Collection collection = this.collectionRepository.getById(id);
            if (collection == null)
                return new ResponseEntity<>("No collection!", HttpStatus.OK);
            return new ResponseEntity<>(this.ow.writeValueAsString(collection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateCollection(Collection data) {
        try {
            Collection collection = this.collectionRepository.getById(data.getId());
            collection = data;
            this.collectionRepository.save(collection);
            return new ResponseEntity<>(this.ow.writeValueAsString(collection), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
}
