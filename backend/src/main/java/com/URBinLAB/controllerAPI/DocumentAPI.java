package com.URBinLAB.controllerAPI;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface DocumentAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("type") String type,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link);


    @RequestMapping(value = "/add_aerial_image", method = RequestMethod.POST)
    ResponseEntity<String> createAerialImage(@RequestHeader MultiValueMap<String, String> map,
                                             @PathParam("name") String name,
                                             @PathParam("description") String description,
                                             @PathParam("type") String type,
                                             @PathParam("provider") String provider,
                                             @PathParam("timeScope") Date timeScope,
                                             @PathParam("link") String link,
                                             @PathParam("scale") Integer scale);


    @RequestMapping(value = "/add_aerial_photography", method = RequestMethod.POST)
    ResponseEntity<String> createAerialPhotography(@RequestHeader MultiValueMap<String, String> map,
                                                   @PathParam("name") String name,
                                                   @PathParam("description") String description,
                                                   @PathParam("type") String type,
                                                   @PathParam("provider") String provider,
                                                   @PathParam("timeScope") Date timeScope,
                                                   @PathParam("link") String link,
                                                   @PathParam("scale") Integer scale,
                                                   @PathParam("resolution") String resolution);


    @RequestMapping(value = "/add_photography", method = RequestMethod.POST)
    ResponseEntity<String> createPhotography(@RequestHeader MultiValueMap<String, String> map,
                                             @PathParam("name") String name,
                                             @PathParam("description") String description,
                                             @PathParam("type") String type,
                                             @PathParam("provider") String provider,
                                             @PathParam("timeScope") Date timeScope,
                                             @PathParam("link") String link,
                                             @PathParam("resolution") String resolution);


    @RequestMapping(value = "/add_cartography", method = RequestMethod.POST)
    ResponseEntity<String> createCartography(@RequestHeader MultiValueMap<String, String> map,
                                             @PathParam("name") String name,
                                             @PathParam("description") String description,
                                             @PathParam("type") String type,
                                             @PathParam("provider") String provider,
                                             @PathParam("timeScope") Date timeScope,
                                             @PathParam("link") String link,
                                             @PathParam("scale") Integer scale,
                                             @PathParam("format") String format);


    @RequestMapping(value = "/add_thematic_map", method = RequestMethod.POST)
    ResponseEntity<String> createThematicMap(@RequestHeader MultiValueMap<String, String> map,
                                             @PathParam("name") String name,
                                             @PathParam("description") String description,
                                             @PathParam("type") String type,
                                             @PathParam("provider") String provider,
                                             @PathParam("timeScope") Date timeScope,
                                             @PathParam("link") String link,
                                             @PathParam("scale") Integer scale,
                                             @PathParam("resolution") String resolution,
                                             @PathParam("theme") String theme);
}
