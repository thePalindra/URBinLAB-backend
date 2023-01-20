package com.URBinLAB.lists;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface ListsAPI {

    @RequestMapping(value = "/startup", method = RequestMethod.POST)
    ResponseEntity<String> createOnSignup(@RequestParam("id") Long id);

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> create(@RequestParam("token") String map,
                                  @RequestParam("name") String name);

    @RequestMapping(value = "/add_to_list", method = RequestMethod.POST)
    ResponseEntity<String> create(@RequestParam("list") Long listId,
                                  @RequestParam("doc") Long docId);

    @RequestMapping(value = "/add_to_historic", method = RequestMethod.POST)
    ResponseEntity<String> addToHistoric(@RequestParam("token") String map,
                                         @RequestParam("id") Long id);

    @RequestMapping(value = "/add_to_fav", method = RequestMethod.POST)
    ResponseEntity<String> addToFavourite(@RequestParam("token") String map,
                                          @RequestParam("doc") Long id);

    @RequestMapping(value = "/get_all", method = RequestMethod.POST)
    ResponseEntity<String> getAll(@RequestParam("token") String map);

    @RequestMapping(value = "/get_by_name", method = RequestMethod.POST)
    ResponseEntity<String> getByName(@RequestParam("token") String map,
                                     @RequestParam("name") String name);

    @RequestMapping(value = "/update_name", method = RequestMethod.POST)
    ResponseEntity<String> changeName(@RequestParam("name") String name,
                                      @RequestParam("id") Long id);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<String> changeName(@RequestParam("id") Long id);
}
