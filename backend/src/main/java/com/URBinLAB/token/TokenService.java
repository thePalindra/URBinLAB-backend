package com.URBinLAB.token;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class TokenService {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private boolean tokenChecker (MultiValueMap<String, String> map,
                                  Feature feature) {
        try {
            if (!map.containsKey("token"))
                return AccessControl.access(feature, "all");

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = new Gson().fromJson(token, Token.class);

            Token toCompare = this.tokenRepository.getById(temp.getId());
            if (!temp.getToken().equals(toCompare.getToken()))
                return false;

            if (System.currentTimeMillis() > temp.getLogin().getTime() + AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            return false;
        }
    }

    private Feature typeChanger(String type) {
        switch (type) {
            case "A":
                return Feature.ALL;
            case "M":
                return Feature.ONLY_MASTER;
            case "R":
                return Feature.RESEARCHER_OR_ABOVE;
            default:
                return null;
        }
    }

    public ResponseEntity<String> checkToken(MultiValueMap<String, String> map, String type) {
        if (this.tokenChecker(map, this.typeChanger(type)))
            return new ResponseEntity<>(new Gson().toJson("Deleted successfully!"), HttpStatus.OK);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
