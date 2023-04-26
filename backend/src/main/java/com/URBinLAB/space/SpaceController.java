package com.URBinLAB.space;

import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/space")
public class SpaceController implements SpaceAPI {

    @Autowired
    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }


    @Override
    public ResponseEntity<String> attachSpace(Long id,
                                              Long document) {
        return this.spaceService.attachSpace(id, document);
    }

    @Override
    public ResponseEntity<String> addSpace(Long document,
                                           String space) {
        return this.spaceService.addSpace(document, space);
    }

    @Override
    public ResponseEntity<String> addSpaceGeo(Long document,
                                              String space,
                                              String name) {
        return this.spaceService.addFileSpace(document, space, name);
    }

    @Override
    public ResponseEntity<String> addCircle(Long document,
                                            Double lng,
                                            Double lat,
                                            Double size) {
        return this.spaceService.addCircle(document, lng, lat, size);
    }

    @Override
    public ResponseEntity<String> getAllFromLevel(Integer level) {
        return this.spaceService.getAllFromLevel(level);
    }

    @Override
    public ResponseEntity<String> searchByName(String name,
                                               String level,
                                               String hierarchy) {
        return this.spaceService.searchByName(name, level, hierarchy);
    }

    @Override
    public ResponseEntity<String> getAllHierarchyTypes() {
        return this.spaceService.getAllHierarchyTypes();
    }

    @Override
    public ResponseEntity<String> getAllHierarchies(String type) {
        return this.spaceService.getAllHierarchies(type);
    }

    @Override
    public ResponseEntity<String> getAllLevels(String hierarchy) {
        return this.spaceService.getAllLevels(hierarchy);
    }

    @Override
    public ResponseEntity<String> getAllNamesFromLevel(String hierarchy,
                                                       String level) {
        return this.spaceService.getAllNamesFromLevel(hierarchy, level);
    }

    @Override
    public ResponseEntity<String> getAllSpaces() {
        return this.spaceService.getAllSpaces();
    }
}
