package com.URBinLAB.controllerAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

public interface SpaceAPI {

    @RequestMapping(value = "/attach", method = RequestMethod.POST)
    ResponseEntity<String> attachSpace(@RequestHeader MultiValueMap<String, String> map,
                                       @PathParam("id") Long id,
                                       @PathParam("document") Long document);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> addSpace(@RequestHeader MultiValueMap<String, String> map,
                                    @PathParam("document") Long document,
                                    @PathParam("space") String space);

    @RequestMapping(value = "/get_all_from_level", method = RequestMethod.POST)
    ResponseEntity<String> getAllFromLevel(@RequestHeader MultiValueMap<String, String> map,
                                           @PathParam("level") Integer level);
}
