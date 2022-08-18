package com.URBinLAB.controllerAPI;

import com.URBinLAB.domains.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CollectionAPI {

    @RequestMapping(value = "/get_all", method = RequestMethod.POST)
    ResponseEntity<String> getCollections(@RequestHeader MultiValueMap<String, String> map);

}
