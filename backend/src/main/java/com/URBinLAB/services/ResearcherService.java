package com.URBinLAB.services;

import com.URBinLAB.domains.Researcher;
import com.URBinLAB.domains.Token;
import com.URBinLAB.repositories.ResearcherRepository;
import com.URBinLAB.repositories.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ResearcherService {

    private ResearcherRepository researcherRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public ResearcherService (ResearcherRepository researcherRepository, TokenRepository tokenRepository) {
        this.researcherRepository = researcherRepository;
        this.tokenRepository = tokenRepository;
    }

    public boolean tokenChecker (MultiValueMap<String, String> map, Feature feature) {
        try {
            if (!map.containsKey("token"))
                return AccessControl.access(feature, "all");

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Token toCompare = this.tokenRepository.getById(temp.getId());
            if (!temp.getToken().equals(toCompare.getToken()))
                return false;


            if (System.currentTimeMillis() > temp.getLogin().getTime() + AccessControl.TIME) {
                this.tokenRepository.delete(toCompare);
                return false;
            }

            return AccessControl.access(feature, temp.getRole());
        } catch (Exception e) {
            System.out.println("Oh Oh");
            return false;
        }
    }

    public ResponseEntity<String> signUp(String name
            , String email
            , String password){

        try {

            if (this.researcherRepository.getByName(name) != null || this.researcherRepository.getByEmail(email) != null)
                return new ResponseEntity<>("User already exists!", HttpStatus.FORBIDDEN);
            Researcher user = Researcher.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .role("admin")
                    .build();
            user.sha256Pass();
            this.researcherRepository.save(user);

            return new ResponseEntity<>(this.gson.toJson("Account created!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> login(String name,
                                        String password,
                                        String device) {

        try {

            Researcher user = this.researcherRepository.login(name, DigestUtils.sha256Hex(password));
            if (user == null)
                return new ResponseEntity<>(this.gson.toJson("Utilizador não existe ou password errada!"), HttpStatus.UNAUTHORIZED);

            Token token = Token.builder()
                    .device(device)
                    .role(user.getRole())
                    .login(new Date())
                    .researcher(user)
                    .token(UUID.randomUUID().toString())
                    .build();
            token = this.tokenRepository.save(token);

            return new ResponseEntity<>(this.gson.toJson(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> logout(MultiValueMap<String, String> map) {
        try {
            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            this.tokenRepository.deleteById(temp.getId());


            return new ResponseEntity<>(this.gson.toJson("Logged out!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
