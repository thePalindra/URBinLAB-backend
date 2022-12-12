package com.URBinLAB.document.aerial_image.ortos;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface OrtosAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link,
                                          @PathParam("resolution") String resolution,
                                          @PathParam("scale") String scale);

    @RequestMapping(value = "/get_scale", method = RequestMethod.POST)
    ResponseEntity<String> getAllScale(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_resolution", method = RequestMethod.POST)
    ResponseEntity<String> getAllResolution(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_by_id", method = RequestMethod.POST)
    ResponseEntity<String> getById(@RequestHeader MultiValueMap<String, String> map,
                                   @RequestParam("id") Long id);
}
