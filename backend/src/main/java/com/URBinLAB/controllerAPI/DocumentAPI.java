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
                                          @PathParam("collection") Long collectionId,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("type") String type,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link);


    @RequestMapping(value = "/add_aerial_image", method = RequestMethod.POST)
    ResponseEntity<String> createAerialImage(@RequestHeader MultiValueMap<String, String> map,
                                             @PathParam("collection") Long collectionId,
                                             @PathParam("name") String name,
                                             @PathParam("description") String description,
                                             @PathParam("type") String type,
                                             @PathParam("provider") String provider,
                                             @PathParam("timeScope") Date timeScope,
                                             @PathParam("link") String link,
                                             @PathParam("scale") Integer scale);


    @RequestMapping(value = "/add_aerial_photography", method = RequestMethod.POST)
    ResponseEntity<String> createAerialPhotography(@RequestHeader MultiValueMap<String, String> map,
                                                   @PathParam("collection") Long collectionId,
                                                   @PathParam("name") String name,
                                                   @PathParam("description") String description,
                                                   @PathParam("type") String type,
                                                   @PathParam("provider") String provider,
                                                   @PathParam("timeScope") Date timeScope,
                                                   @PathParam("link") String link,
                                                   @PathParam("scale") Integer scale,
                                                   @PathParam("resolution") String resolution);


    @RequestMapping(value = "/add_file", method = RequestMethod.POST)
    ResponseEntity<String> addFile(@RequestHeader MultiValueMap<String, String> map,
                                   @PathParam("file") MultipartFile file,
                                   @PathParam("document") Long document,
                                   @PathParam("name") String name,
                                   @PathParam("format") String format,
                                   @PathParam("creation") Date creation,
                                   @PathParam("size") Long size);

    @RequestMapping(value = "/add_space", method = RequestMethod.POST)
    ResponseEntity<String> addSpace(@RequestHeader MultiValueMap<String, String> map,
                                    @PathParam("space") Long id,
                                    @PathParam("doc") Long document);
}
