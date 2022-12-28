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
    ResponseEntity<String> logout(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/get_archivers", method = RequestMethod.POST)
    ResponseEntity<String> getArchivers();

    @RequestMapping(value = "/archiver_name", method = RequestMethod.POST)
    ResponseEntity<String> getArchiverName(@RequestParam("id") Long id);
}
