package com.URBinLAB.controllerAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

public interface SpaceAPI {

    @RequestMapping(value = "/attach_space", method = RequestMethod.POST)
    ResponseEntity<String> attachSpace(@RequestHeader MultiValueMap<String, String> map,
                                    @PathParam("space") Long id,
                                    @PathParam("doc") Long document);

    @RequestMapping(value = "/get_all_from_level", method = RequestMethod.POST)
    ResponseEntity<String> getAllFromLevel(@RequestHeader MultiValueMap<String, String> map,
                                           @PathParam("level") Integer level);
}
