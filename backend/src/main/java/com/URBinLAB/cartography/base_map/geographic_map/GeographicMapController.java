package com.URBinLAB.cartography.base_map.geographic_map;

import com.URBinLAB.cartography.base_map.BaseMapAPI;
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
@RequestMapping("/geographic_map")
public class GeographicMapController implements BaseMapAPI {

    @Autowired
    private GeographicMapService geographicMapService;

    public GeographicMapController(GeographicMapService geographicMapService) {
        this.geographicMapService = geographicMapService;
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

        if (this.geographicMapService.tokenChecker(map, Feature.ADD_DOCUMENT))
            return this.geographicMapService.createDocument(map, name, description, provider, timeScope, link, scale, raster, resolution, type);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllScale(MultiValueMap<String, String> map) {
        if (this.geographicMapService.tokenChecker(map, Feature.AUX_QUERY))
            return this.geographicMapService.getAllScale();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllImageResolution(MultiValueMap<String, String> map) {
        if (this.geographicMapService.tokenChecker(map, Feature.AUX_QUERY))
            return this.geographicMapService.getAllImageResolution();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getAllGeometryType(MultiValueMap<String, String> map) {
        if (this.geographicMapService.tokenChecker(map, Feature.AUX_QUERY))
            return this.geographicMapService.getAllGeometryType();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
