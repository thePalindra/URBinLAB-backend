package com.URBinLAB.collection;


import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/collection")
public class CollectionController implements CollectionAPI {

    @Autowired
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public ResponseEntity<String> getCollections() {
        return this.collectionService.getAllCollections();
    }

    @Override
    public ResponseEntity<String> createCollection(MultiValueMap<String, String> map,
                                                   String name,
                                                   String description) {
        return this.collectionService.createCollection(map, name, description);
    }


}
