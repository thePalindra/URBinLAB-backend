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

    private boolean tokenChecker (String token,
                                  Feature feature) {
        try {
            if (token==null || token.equals("null"))
                return false;

            Token temp = new Gson().fromJson(token, Token.class);

            Token toCompare = this.tokenRepository.getById(temp.getId());

            if (!temp.getToken().equals(toCompare.getToken())
                    || !toCompare.getResearcher().getActive()
                    || toCompare.getResearcher().getDeleted()
                    || System.currentTimeMillis() > toCompare.getLogin().getTime() + AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }

    private Feature typeChanger(String type) {
        switch (type) {
            case "A":
                return Feature.ALL;
            case "M":
                return Feature.MASTER;
            case "R":
                return Feature.RESEARCHER;
            default:
                return null;
        }
    }

    public ResponseEntity<String> checkToken(String map, String type) {
        if (this.tokenChecker(map, this.typeChanger(type)))
            return new ResponseEntity<>(new Gson().toJson("Ok!"), HttpStatus.OK);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
