package com.URBinLAB.controllerAPI;

import com.URBinLAB.domains.Researcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;


public interface ResearcherAPI {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    ResponseEntity<String> signUp(@RequestHeader MultiValueMap<String, String> map,
                                  @PathParam("name") String name,
                                  @PathParam("email") String email,
                                  @PathParam("password") String password);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<String> login(@RequestHeader MultiValueMap<String, String> map,
                                 @PathParam("name") String name,
                                 @PathParam("password") String password);

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseEntity<String> logout(@RequestHeader MultiValueMap<String, String> map);
}
