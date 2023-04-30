package com.URBinLAB.space;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

public interface SpaceAPI {

    @RequestMapping(value = "/attach", method = RequestMethod.POST)
    ResponseEntity<String> attachSpace(@PathParam("id") Long id,
                                       @PathParam("document") Long document);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> addSpace(@PathParam("document") Long document,
                                    @PathParam("space") String space);

    @RequestMapping(value = "/add_Geo", method = RequestMethod.POST)
    ResponseEntity<String> addSpaceGeo(@PathParam("document") Long document,
                                       @PathParam("space") String space,
                                       @PathParam("name") String name);

    @RequestMapping(value = "/add_circle", method = RequestMethod.POST)
    ResponseEntity<String> addCircle(@PathParam("document") Long document,
                                     @PathParam("lng") Double lng,
                                     @PathParam("lat") Double lat,
                                     @PathParam("size") Double size);

    @RequestMapping(value = "/get_all_from_level", method = RequestMethod.POST)
    ResponseEntity<String> getAllFromLevel(@PathParam("level") Integer level);

    @RequestMapping(value = "/search_by_name", method = RequestMethod.POST)
    ResponseEntity<String> searchByName(@PathParam("name") String name,
                                        @PathParam("level") String level,
                                        @PathParam("hierarchy") String hierarchy);

    @RequestMapping(value = "/get_hierarchy_type", method = RequestMethod.POST)
    ResponseEntity<String> getAllHierarchyTypes();

    @RequestMapping(value = "/get_hierarchy", method = RequestMethod.POST)
    ResponseEntity<String> getAllHierarchies(@PathParam("type") String type);

    @RequestMapping(value = "/get_levels", method = RequestMethod.POST)
    ResponseEntity<String> getAllLevels(@PathParam("hierarchy") String hierarchy);

    @RequestMapping(value = "/get_names", method = RequestMethod.POST)
    ResponseEntity<String> getAllNamesFromLevel(@PathParam("hierarchy") String hierarchy,
                                                @PathParam("level") String level);

    @RequestMapping(value = "/get_spaces", method = RequestMethod.GET)
    ResponseEntity<String> getAllSpaces();

    @RequestMapping(value = "/get_by_id", method = RequestMethod.POST)
    ResponseEntity<String> getAllTheDocumentsBySpaceId(@RequestParam("id") Long id);
}
