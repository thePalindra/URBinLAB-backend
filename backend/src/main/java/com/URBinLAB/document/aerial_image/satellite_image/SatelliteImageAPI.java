package com.URBinLAB.document.aerial_image.satellite_image;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface SatelliteImageAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestParam("token") String map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link,
                                          @PathParam("satellite") String satellite,
                                          @PathParam("resolution") String resolution);

    @RequestMapping(value = "/get_resolution", method = RequestMethod.POST)
    ResponseEntity<String> getAllResolution();

    @RequestMapping(value = "/get_satellite", method = RequestMethod.POST)
    ResponseEntity<String> getAllSatellite();

    @RequestMapping(value = "/get_by_id", method = RequestMethod.POST)
    ResponseEntity<String> getById(@RequestParam("id") Long id);
}
