package com.URBinLAB.document.cartography.base_map.topographic_map;

import com.URBinLAB.document.cartography.base_map.BaseMapAPI;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/topographic_map")
public class TopographicMapController implements BaseMapAPI {

    @Autowired
    private TopographicMapService topographicMapService;

    public TopographicMapController(TopographicMapService topographicMapService) {
        this.topographicMapService = topographicMapService;
    }

    @Override
    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String scale,
                                                 Boolean raster,
                                                 String resolution,
                                                 String type) {

        if (this.topographicMapService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.topographicMapService.createDocument(map, name, description, provider, timeScope, link, scale, raster, resolution, type);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllImageResolution(MultiValueMap<String, String> map) {
        return null;
    }

    @Override
    public ResponseEntity<String> getAllScale(MultiValueMap<String, String> map) {
        return null;
    }

    @Override
    public ResponseEntity<String> getAllGeometryType(MultiValueMap<String, String> map) {
        return null;
    }

    @Override
    public ResponseEntity<String> getById(MultiValueMap<String, String> map, Long id) {
        return null;
    }
}