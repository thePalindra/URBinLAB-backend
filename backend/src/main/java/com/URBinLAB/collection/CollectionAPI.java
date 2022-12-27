package com.URBinLAB.collection;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;

public interface CollectionAPI {

    @RequestMapping(value = "/get_all", method = RequestMethod.POST)
    ResponseEntity<String> getCollections(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> createCollection(@RequestHeader MultiValueMap<String, String> map,
                                            @PathParam("name") String name,
                                            @PathParam("description") String description);
}
