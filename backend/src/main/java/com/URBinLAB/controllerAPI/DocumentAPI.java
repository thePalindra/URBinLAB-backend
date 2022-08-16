package com.URBinLAB.controllerAPI;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface DocumentAPI {

    @RequestMapping(value = "/add_document", method = RequestMethod.POST)
    ResponseEntity<String> createDocument(@RequestHeader MultiValueMap<String, String> map,
                                          @PathParam("name") String name,
                                          @PathParam("description") String description,
                                          @PathParam("provider") String provider,
                                          @PathParam("timeScope") Date timeScope,
                                          @PathParam("link") String link);

    @RequestMapping(value = "/get_document_by_space_geometry", method = RequestMethod.POST)
    ResponseEntity<String> getDocumentBySpaceGeometry(@RequestHeader MultiValueMap<String, String> map,
                                                      @PathParam("space") String space,
                                                      @PathParam("page") Integer page);

}
