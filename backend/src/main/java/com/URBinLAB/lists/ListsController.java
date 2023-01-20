package com.URBinLAB.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/lists")
public class ListsController implements ListsAPI {

    @Autowired
    private ListsService listsService;

    @Override
    public ResponseEntity<String> createOnSignup(Long id) {
        return this.listsService.createOnSignup(id);
    }

    @Override
    public ResponseEntity<String> create(String map, String name) {
        return this.listsService.create(map, name);
    }

    @Override
    public ResponseEntity<String> create(Long listId, Long docId) {
        return this.listsService.addToList(listId, docId);
    }

    @Override
    public ResponseEntity<String> addToHistoric(String map,
                                                Long id) {
        return this.listsService.addToHistoric(map, id);
    }

    @Override
    public ResponseEntity<String> addToFavourite(String map, Long id) {
        return this.listsService.addToFavourite(map, id);
    }

    @Override
    public ResponseEntity<String> getAll(String map) {
        return this.listsService.getAll(map);
    }

    @Override
    public ResponseEntity<String> getByName(String map, String name) {
        return this.listsService.getByName(map, name);
    }

    @Override
    public ResponseEntity<String> changeName(String name, Long id) {
        return this.listsService.changeName(name, id);
    }

    @Override
    public ResponseEntity<String> changeName(Long id) {
        return this.listsService.delete(id);
    }
}
