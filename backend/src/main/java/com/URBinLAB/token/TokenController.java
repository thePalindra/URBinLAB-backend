package com.URBinLAB.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/token")
public class TokenController implements TokenAPI{

    @Autowired
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public ResponseEntity<String> checkToken(MultiValueMap<String, String> map, String type) {
        return this.tokenService.checkToken(map, type);
    }
}
