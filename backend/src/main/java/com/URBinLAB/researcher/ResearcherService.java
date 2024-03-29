package com.URBinLAB.researcher;

import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;
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
import java.util.UUID;

@Service
public class ResearcherService {

    private ResearcherRepository researcherRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public ResearcherService (ResearcherRepository researcherRepository,
                              TokenRepository tokenRepository) {
        this.researcherRepository = researcherRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> signUp(String name,
                                         String email,
                                         String password){

        try {

            if (this.researcherRepository.getByName(name) != null || this.researcherRepository.getByEmail(email) != null)
                return new ResponseEntity<>("User already exists!", HttpStatus.FORBIDDEN);

            Researcher user = Researcher.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .role("all")
                    .active(false)
                    .deleted(false)
                    .build();
            user.sha256Pass();
            user = this.researcherRepository.save(user);

            return new ResponseEntity<>(this.gson.toJson(user.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> login(String name,
                                        String password) {

        try {

            if (this.researcherRepository.login(name, DigestUtils.sha256Hex(password)) == null)
                return new ResponseEntity<>(this.gson.toJson("Utilizador não existe ou password errada!"), HttpStatus.UNAUTHORIZED);

            Researcher user = this.researcherRepository.login(name, DigestUtils.sha256Hex(password));

            if (this.tokenRepository.getTokenByUser(user) != null)
                this.tokenRepository.delete(this.tokenRepository.getTokenByUser(user));

            Token token = Token.builder()
                    .role(user.getRole())
                    .login(new Date())
                    .researcher(user)
                    .token(UUID.randomUUID().toString())
                    .build();
            token = this.tokenRepository.save(token);

            return new ResponseEntity<>(this.gson.toJson(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> logout(String map) {
        try {
            Token temp = gson.fromJson(map, Token.class);

            this.tokenRepository.deleteById(temp.getId());

            return new ResponseEntity<>(this.gson.toJson("Logged out!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getArchivers() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.researcherRepository.getAllArchivers()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getArchiverName(Long id) {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.researcherRepository.getArchiverName(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getAllInactive() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.researcherRepository.getAllInactive()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getAllDeleted() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.researcherRepository.getAllDeleted()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> activate(Long id,
                                           String role) {
        try {
            Researcher saved = this.researcherRepository.getById(id);

            saved.activate();
            saved.alterRole(role);

            this.researcherRepository.save(saved);
            return new ResponseEntity<>(this.gson.toJson("ok"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> delete(Long id) {
        try {
            Researcher saved = this.researcherRepository.getById(id);

            saved.delete();

            this.researcherRepository.save(saved);
            return new ResponseEntity<>(this.gson.toJson("ok"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> fullDelete(Long id) {
        try {
            this.researcherRepository.deleteById(id);

            return new ResponseEntity<>(this.gson.toJson("ok"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getAllActive() {
        try {
            return new ResponseEntity<>(this.gson.toJson(this.researcherRepository.getAllActive()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
