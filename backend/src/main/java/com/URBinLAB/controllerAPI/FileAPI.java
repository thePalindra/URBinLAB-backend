package com.URBinLAB.controllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Date;

public interface FileAPI {

    @RequestMapping(value = "/add_file", method = RequestMethod.POST)
    ResponseEntity<String> addFile(@RequestHeader MultiValueMap<String, String> map,
                                   @PathParam("file") MultipartFile file,
                                   @PathParam("document") Long document,
                                   @PathParam("name") String name,
                                   @PathParam("format") String format,
                                   @PathParam("creation") Date creation,
                                   @PathParam("size") Long size);

}
