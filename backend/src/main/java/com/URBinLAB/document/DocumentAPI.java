package com.URBinLAB.document;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface DocumentAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestParam("token") String map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link);

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    ResponseEntity<String> getAll(@RequestParam("limit") Long limit);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<String> deleteDocument(@RequestParam("id") Long id);

    @RequestMapping(value = "/get_document_by_space_geometry", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentBySpaceGeometry(@PathParam("space") String space,
                                                      @PathParam("page") Integer page);

    @RequestMapping(value = "/get_document_by_space_marker", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentBySpaceMarker(@PathParam("space") String space,
                                                    @PathParam("page") Integer page);

    @RequestMapping(value = "/get_document_by_space_circle", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentBySpaceCircle(@PathParam("lng") Double lng,
                                                    @PathParam("lat") Double lat,
                                                    @PathParam("size") Double size,
                                                    @PathParam("page") Integer page);

    @RequestMapping(value = "/big_query", method = RequestMethod.POST)
    ResponseEntity<String> bigQuery(@RequestParam("name") String name,
                                    @RequestParam("provider") String provider,
                                    @RequestParam("max") Integer max,
                                    @RequestParam("min") Integer min,
                                    @RequestParam("archiver") Long archiver,
                                    @RequestParam("types") String[] types,
                                    @RequestParam("page") Integer page);

    @RequestMapping(value = "/get_all_providers", method = RequestMethod.POST)
    ResponseEntity<String> getAllProviders();

    @RequestMapping(value = "/get_all_urls", method = RequestMethod.POST)
    ResponseEntity<String> getAllURLs();

    @RequestMapping(value = "/from_list", method = RequestMethod.POST)
    ResponseEntity<String> fromList(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/get_space", method = RequestMethod.POST)
    ResponseEntity<String> getSpaceFromDocument(@RequestParam("id") Long id);

    @RequestMapping(value = "/get_by_name_in_list", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentByName(@RequestParam("name") String name,
                                             @RequestParam("list") Integer[] list);

    @RequestMapping(value = "/group_provider_list", method = RequestMethod.POST)
    ResponseEntity<String> groupByProvider(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/group_year_list", method = RequestMethod.POST)
    ResponseEntity<String> groupByYear(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/group_type_list", method = RequestMethod.POST)
    ResponseEntity<String> groupByType(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/group_archivist_list", method = RequestMethod.POST)
    ResponseEntity<String> groupByArchiver(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/group_provider", method = RequestMethod.POST)
    ResponseEntity<String> groupByProvider();

    @RequestMapping(value = "/group_year", method = RequestMethod.POST)
    ResponseEntity<String> groupByYear();

    @RequestMapping(value = "/group_type", method = RequestMethod.POST)
    ResponseEntity<String> groupByType();

    @RequestMapping(value = "/group_archivist", method = RequestMethod.POST)
    ResponseEntity<String> groupByArchiver();

    @RequestMapping(value = "/filter_list", method = RequestMethod.POST)
    ResponseEntity<String> filter(@RequestParam("years") Integer[] years,
                                  @RequestParam("providers") String[] providers,
                                  @RequestParam("archivers") Integer[] archivers,
                                  @RequestParam("types") String[] types,
                                  @RequestParam("list") Integer[] list);

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    ResponseEntity<String> filter(@RequestParam("years") Integer[] years,
                                  @RequestParam("providers") String[] providers,
                                  @RequestParam("archivers") Integer[] archivers,
                                  @RequestParam("types") String[] types);

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentByName(@RequestParam("name") String name);


    @RequestMapping(value = "/year_asc", method = RequestMethod.POST)
    ResponseEntity<String> orderByYearAsc(@RequestParam("list") Integer[] list);


    @RequestMapping(value = "/year_desc", method = RequestMethod.POST)
    ResponseEntity<String> orderByYearDesc(@RequestParam("list") Integer[] list);


    @RequestMapping(value = "/name_asc", method = RequestMethod.POST)
    ResponseEntity<String> orderByNameAsc(@RequestParam("list") Integer[] list);


    @RequestMapping(value = "/name_desc", method = RequestMethod.POST)
    ResponseEntity<String> orderByNameDesc(@RequestParam("list") Integer[] list);

    @RequestMapping(value = "/by_id", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentById(@RequestParam("id") Long id);

    @RequestMapping(value = "/add_collection", method = RequestMethod.POST)
    ResponseEntity<String> addCollection(@RequestParam("document") Long id,
                                         @RequestParam("collection") Long collection);
}
