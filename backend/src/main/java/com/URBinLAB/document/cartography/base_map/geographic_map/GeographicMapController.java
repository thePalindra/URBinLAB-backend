package com.URBinLAB.document.cartography.base_map.geographic_map;

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
        return this.geographicMapService.createDocument(map, name, description, provider, timeScope, link, scale, raster, resolution, type);
    }

    @Override
    public ResponseEntity<String> getAllScale() {
        return this.geographicMapService.getAllScale();
    }

    @Override
    public ResponseEntity<String> getAllImageResolution() {
        return this.geographicMapService.getAllImageResolution();
    }

    @Override
    public ResponseEntity<String> getAllGeometryType() {
        return this.geographicMapService.getAllGeometryType();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.geographicMapService.getById(id);
    }
}
