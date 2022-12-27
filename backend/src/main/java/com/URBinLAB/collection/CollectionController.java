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
    public ResponseEntity<String> getCollections(MultiValueMap<String, String> map) {
        if (this.collectionService.tokenChecker(map, Feature.SPATIAL_QUERY))
            return this.collectionService.getAllCollections();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> createCollection(MultiValueMap<String, String> map,
                                                   String name,
                                                   String description) {

        if (this.collectionService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.collectionService.createCollection(map, name, description);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }


}
