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
    public ResponseEntity<String> signUp(String name,
                                         String email,
                                         String password) {
        return this.researcherService.signUp(name, email, password);
    }

    @Override
    public ResponseEntity<String> login(String name,
                                        String password) {
        return this.researcherService.login(name, password);
    }

    @Override
    public ResponseEntity<String> logout(String map) {
        return this.researcherService.logout(map);
    }

    @Override
    public ResponseEntity<String> getArchivers() {
        return this.researcherService.getArchivers();
    }

    @Override
    public ResponseEntity<String> getArchiverName(Long id) {
        return this.researcherService.getArchiverName(id);
    }

    @Override
    public ResponseEntity<String> getAllInactive() {
        return this.researcherService.getAllInactive();
    }

    @Override
    public ResponseEntity<String> getAllDeleted() {
        return this.researcherService.getAllDeleted();
    }

    @Override
    public ResponseEntity<String> getAllActive() {
        return this.researcherService.getAllActive();
    }

    @Override
    public ResponseEntity<String> activate(Long id, String role) {
        return this.researcherService.activate(id, role);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return this.researcherService.delete(id);
    }

    @Override
    public ResponseEntity<String> fullDelete(Long id) {
        return this.researcherService.fullDelete(id);
    }
}
