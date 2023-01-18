package com.URBinLAB.lists;

import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.researcher.Researcher;
import com.URBinLAB.researcher.ResearcherRepository;
import com.URBinLAB.token.Token;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;

@Service
public class ListsService {

    private ListsRepository listsRepository;
    private ResearcherRepository researcherRepository;
    private DocumentRepository documentRepository;

    @Autowired
    public ListsService(ListsRepository listsRepository,
                        ResearcherRepository researcherRepository,
                        DocumentRepository documentRepository) {
        this.listsRepository = listsRepository;
        this.researcherRepository = researcherRepository;
        this.documentRepository = documentRepository;
    }

    public ResponseEntity<String> createOnSignup(Long id) {
        try {
            Researcher temp = this.researcherRepository.getById(id);

            Lists fav = Lists.builder()
                    .name("Favoritos")
                    .created(new Date())
                    .researcher(temp)
                    .build();

            this.listsRepository.save(fav);

            Lists hist = Lists.builder()
                    .name("Histórico")
                    .created(new Date())
                    .researcher(temp)
                    .build();

            this.listsRepository.save(hist);

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addToHistoric(MultiValueMap<String, String> map,
                                                Long id) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            Long listId = this.listsRepository.get(temp.getResearcher().getId(), "Histórico");

            this.listsRepository.addDocument(listId, id, new Date());

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addToList(Long listId, Long docId) {
        try {

            this.listsRepository.addDocument(listId, docId, new Date());

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAll(MultiValueMap<String, String> map) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            return new ResponseEntity<>(new Gson().toJson(this.listsRepository.getAll(temp.getResearcher().getId())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String>   getByName(MultiValueMap<String, String> map,
                                            String name) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            return new ResponseEntity<>(new Gson().toJson(this.listsRepository.getByName(temp.getResearcher().getId(), name)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> create(MultiValueMap<String, String> map,
                                         String name) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            Lists fav = Lists.builder()
                    .name(name)
                    .created(new Date())
                    .researcher(temp.getResearcher())
                    .build();

            fav = this.listsRepository.save(fav);

            return new ResponseEntity<>(new Gson().toJson(fav), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addToFavourite(MultiValueMap<String, String> map, Long id) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            Long listId = this.listsRepository.get(temp.getResearcher().getId(), "Favoritos");

            this.listsRepository.addDocument(listId, id, new Date());

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> changeName(String name,
                                             Long id) {
        try {

            Lists tempList = this.listsRepository.getById(id);
            tempList.setName(name);

            tempList = this.listsRepository.save(tempList);

            return new ResponseEntity<>(new Gson().toJson(tempList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> delete(Long id) {
        try {
            this.listsRepository.deleteById(id);
            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Gson().toJson("Something went wrong"), HttpStatus.BAD_REQUEST);
        }
    }
}
