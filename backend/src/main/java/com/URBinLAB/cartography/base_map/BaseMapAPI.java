package com.URBinLAB.cartography.base_map;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface BaseMapAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link,
                                          @PathParam("scale") String scale,
                                          @PathParam("raster") Boolean raster,
                                          @PathParam("resolution") String resolution,
                                          @PathParam("type") String type);

    @RequestMapping(value = "/get_image_resolution", method = RequestMethod.POST)
    ResponseEntity<String> getAllImageResolution(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_scale", method = RequestMethod.POST)
    ResponseEntity<String> getAllScale(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_geometry_type", method = RequestMethod.POST)
    ResponseEntity<String> getAllGeometryType(@RequestHeader MultiValueMap<String, String> map);
}
