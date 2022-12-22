package com.URBinLAB.keywords;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

public interface KeywordAPI {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> addKeyword(@RequestHeader MultiValueMap<String, String> map,
                                      @RequestParam("keyword") String keyword);

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    ResponseEntity<String> getKeywordByKeyword(@RequestHeader MultiValueMap<String, String> map,
                                               @RequestParam("keyword") String keyword);

    @RequestMapping(value = "/get_all", method = RequestMethod.POST)
    ResponseEntity<String> getAll(@RequestHeader MultiValueMap<String, String> map);

    @RequestMapping(value = "/document", method = RequestMethod.POST)
    ResponseEntity<String> addKeywordToDocument(@RequestHeader MultiValueMap<String, String> map,
                                                @RequestParam("document") Long document,
                                                @RequestParam("keywords") List<Long> keywords);

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    ResponseEntity<String> groupByKeyword(@RequestHeader MultiValueMap<String, String> map);
}
