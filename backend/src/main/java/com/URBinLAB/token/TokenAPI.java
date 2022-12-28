package com.URBinLAB.token;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface TokenAPI {

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    ResponseEntity<String> checkToken(@RequestHeader MultiValueMap<String, String> map,
                                      @RequestParam("type") String type);


}
