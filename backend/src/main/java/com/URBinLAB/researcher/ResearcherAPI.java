package com.URBinLAB.researcher;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;


public interface ResearcherAPI {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    ResponseEntity<String> signUp(@PathParam("name") String name,
                                  @PathParam("email") String email,
                                  @PathParam("password") String password);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<String> login(@PathParam("name") String name,
                                 @PathParam("password") String password);

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseEntity<String> logout(@RequestParam("token") String map);

    @RequestMapping(value = "/get_archivers", method = RequestMethod.POST)
    ResponseEntity<String> getArchivers();

    @RequestMapping(value = "/archiver_name", method = RequestMethod.POST)
    ResponseEntity<String> getArchiverName(@RequestParam("id") Long id);

    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    ResponseEntity<String> getAllInactive();

    @RequestMapping(value = "/deleted", method = RequestMethod.GET)
    ResponseEntity<String> getAllDeleted();

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    ResponseEntity<String> getAllActive();

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    ResponseEntity<String> activate(@RequestParam("id") Long id,
                                    @RequestParam("role") String role);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<String> delete(@RequestParam("id") Long id);

    @RequestMapping(value = "/full_delete", method = RequestMethod.POST)
    ResponseEntity<String> fullDelete(@RequestParam("id") Long id);
}
