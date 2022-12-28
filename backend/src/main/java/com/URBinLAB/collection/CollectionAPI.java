package com.URBinLAB.collection;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

public interface CollectionAPI {

    @RequestMapping(value = "/get_all", method = RequestMethod.POST)
    ResponseEntity<String> getCollections();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> createCollection(@RequestHeader MultiValueMap<String, String> map,
                                            @RequestParam("name") String name,
                                            @RequestParam("description") String description);
}
