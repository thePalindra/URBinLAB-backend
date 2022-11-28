package com.URBinLAB.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface ReportsAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link,
                                          @PathParam("context") String context,
                                          @PathParam("theme") String theme);

    @RequestMapping(value = "/get_context", method = RequestMethod.POST)
    ResponseEntity<String> getAllContext(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_theme", method = RequestMethod.POST)
    ResponseEntity<String> getAllTheme(@RequestHeader MultiValueMap<String, String> map);
}
