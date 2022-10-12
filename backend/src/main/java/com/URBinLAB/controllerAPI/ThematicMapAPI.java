package com.URBinLAB.controllerAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface ThematicMapAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link,
                                          @PathParam("scale") Integer scale,
                                          @PathParam("raster") Boolean raster,
                                          @PathParam("resolution") String resolution,
                                          @PathParam("type") String type,
                                          @PathParam("theme") String theme,
                                          @PathParam("mapType") String mapType);

    @RequestMapping(value = "/get_theme", method = RequestMethod.POST)
    ResponseEntity<String> getAllTheme(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_type", method = RequestMethod.POST)
    ResponseEntity<String> getAllThematicMapType(@RequestHeader MultiValueMap<String, String> map);
}
