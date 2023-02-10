package com.URBinLAB.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface FileAPI {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<String> addFile(@PathParam("file") MultipartFile file,
                                   @PathParam("document") Long document) throws IOException;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    ResponseEntity<String> getFiles(@PathParam("id") Long id);

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    ResponseEntity<String> deleteFile(@RequestParam("id") Long id);

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResponseEntity<String> updateFile(@RequestParam("id") Long id,
                                      @RequestParam("document") Long document);

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    ResponseEntity<Resource> getFilesToDownload(@RequestParam("id") Long id) throws FileNotFoundException;
}
