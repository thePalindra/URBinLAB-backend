package com.URBinLAB.document.aerial_image.aerial_photography;

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
@RequestMapping("/aerial_photography")
public class AerialPhotographyController implements AerialPhotographyAPI {

    @Autowired
    private AerialPhotographyService aerialPhotographyService;

    public AerialPhotographyController(AerialPhotographyService aerialPhotographyService) {
        this.aerialPhotographyService = aerialPhotographyService;
    }

    @Override
    public ResponseEntity<String> createAerialPhotography(MultiValueMap<String, String> map,
                                                          String name,
                                                          String description,
                                                          String provider,
                                                          Date timeScope,
                                                          String link,
                                                          String scale,
                                                          String resolution) {
        return this.aerialPhotographyService.createAerialPhotography(map, name, description, provider, timeScope, link, scale, resolution);
    }

    @Override
    public ResponseEntity<String> getAllScale() {
        return this.aerialPhotographyService.getAllScale();
    }

    @Override
    public ResponseEntity<String> getAllImageResolution() {
        return this.aerialPhotographyService.getAllImageResolution();
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return this.aerialPhotographyService.getById(id);
    }
}
