package com.URBinLAB.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Date;

public interface FileAPI {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> addFile(@RequestHeader MultiValueMap<String, String> map,
                                   @PathParam("file") MultipartFile file,
                                   @PathParam("document") Long document) throws IOException;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    ResponseEntity<String> getFiles(@RequestHeader MultiValueMap<String, String> map,
                                    @PathParam("id") Long id);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<String> deleteFile(@RequestHeader MultiValueMap<String, String> map,
                                      @RequestParam("id") Long id);
}
