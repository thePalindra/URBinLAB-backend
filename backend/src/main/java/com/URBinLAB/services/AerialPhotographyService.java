package com.URBinLAB.services;

import com.URBinLAB.domains.*;
import com.URBinLAB.repositories.AerialImageRepository;
import com.URBinLAB.repositories.AerialPhotographyRepository;
import com.URBinLAB.repositories.DocumentRepository;
import com.URBinLAB.repositories.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;

@Service
public class AerialPhotographyService {

    private DocumentRepository documentRepository;
    private AerialImageRepository aerialImageRepository;
    private AerialPhotographyRepository aerialPhotographyRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public AerialPhotographyService (DocumentRepository documentRepository,
                                     AerialImageRepository aerialImageRepository,
                                     AerialPhotographyRepository aerialPhotographyRepository,
                                     TokenRepository tokenRepository) {
        this.aerialImageRepository = aerialImageRepository;
        this.aerialPhotographyRepository = aerialPhotographyRepository;
        this.documentRepository = documentRepository;
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
            return false;
        }
    }

    public ResponseEntity<String> createAerialPhotography(MultiValueMap<String,
                                                          String> map,
                                                          String name,
                                                          String description,
                                                          String provider,
                                                          Date timeScope,
                                                          String link,
                                                          Integer scale,
                                                          String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("AERIAL PHOTOGRAPHY")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            AerialImage image = AerialImage.builder()
                    .document(document)
                    .build();

            image = this.aerialImageRepository.save(image);

            AerialPhotography photography = AerialPhotography.builder()
                    .aerialImage(image)
                    .resolution(resolution)
                    .scale(scale)
                    .build();

            photography = this.aerialPhotographyRepository.save(photography);

            return new ResponseEntity<>(new Gson().toJson(photography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
