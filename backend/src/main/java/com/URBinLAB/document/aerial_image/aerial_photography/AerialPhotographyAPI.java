package com.URBinLAB.document.aerial_image.aerial_photography;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface AerialPhotographyAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createAerialPhotography(@RequestHeader MultiValueMap<String, String> map,
                                                   @PathParam("name") String name,
                                                   @PathParam("description") String description,
                                                   @PathParam("provider") String provider,
                                                   @PathParam("timeScope") Date timeScope,
                                                   @PathParam("link") String link,
                                                   @PathParam("scale") String scale,
                                                   @PathParam("resolution") String resolution);

    @RequestMapping(value = "/get_scale", method = RequestMethod.POST)
    ResponseEntity<String> getAllScale();

    @RequestMapping(value = "/get_image_resolution", method = RequestMethod.POST)
    ResponseEntity<String> getAllImageResolution();

    @RequestMapping(value = "/get_by_id", method = RequestMethod.POST)
    ResponseEntity<String> getById(@RequestParam("id") Long id);
}
