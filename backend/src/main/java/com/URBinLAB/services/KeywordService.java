package com.URBinLAB.services;

import com.URBinLAB.domains.Keyword;
import com.URBinLAB.repositories.KeywordRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KeywordService {

    private KeywordRepository keywordRepository;
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public ResponseEntity<String> createKeyword(String keyword) throws JsonProcessingException {
        try {
            Keyword data = Keyword.builder()
                    .keyword(keyword)
                    .build();
            return new ResponseEntity<>(this.ow.writeValueAsString(this.keywordRepository.save(data)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.ow.writeValueAsString("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> selectKeywordById (Long id) throws JsonProcessingException {
        try {
            return new ResponseEntity<>(this.ow.writeValueAsString(this.keywordRepository.getById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.ow.writeValueAsString("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getKeywordByName (String keyword) throws JsonProcessingException {
        try {
            return new ResponseEntity<>(this.ow.writeValueAsString(this.keywordRepository.getKeywordByName(keyword)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.ow.writeValueAsString("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getAllKeywords (Long page) throws JsonProcessingException {
        try {
            Pageable pageable = PageRequest.of(Math.toIntExact(page),5);
            return new ResponseEntity<>(this.ow.writeValueAsString(this.keywordRepository.findAll(pageable)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.ow.writeValueAsString("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
