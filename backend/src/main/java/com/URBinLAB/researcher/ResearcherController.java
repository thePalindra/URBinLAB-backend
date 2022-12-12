package com.URBinLAB.researcher;

import com.URBinLAB.researcher.ResearcherAPI;

import com.URBinLAB.researcher.ResearcherService;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class ResearcherController implements ResearcherAPI {

    private final ResearcherService researcherService;

    @Autowired
    public ResearcherController(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @Override
    public ResponseEntity<String> signUp(MultiValueMap<String, String> map,
                                         String name,
                                         String email,
                                         String password) {
        if (this.researcherService.tokenChecker(map, Feature.SIGNUP))
            return this.researcherService.signUp(name, email, password);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> login(MultiValueMap<String, String> map,
                                        String name,
                                        String password) {
        if (this.researcherService.tokenChecker(map, Feature.LOGIN))
            return this.researcherService.login(name, password);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> logout(MultiValueMap<String, String> map) {
        if (this.researcherService.tokenChecker(map, Feature.LOGOUT))
            return this.researcherService.logout(map);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getArchivers(MultiValueMap<String, String> map) {
        if (this.researcherService.tokenChecker(map, Feature.AUX_QUERY))
            return this.researcherService.getArchivers();

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<String> getArchiverName(MultiValueMap<String, String> map,
                                                  Long id) {
        if (this.researcherService.tokenChecker(map, Feature.AUX_QUERY))
            return this.researcherService.getArchiverName(id);

        return new ResponseEntity<>(new Gson().toJson("How did you get here?!"), HttpStatus.FORBIDDEN);
    }
}
