package com.URBinLAB.document.aerial_image.satellite_image;

import com.URBinLAB.document.aerial_image.AerialImage;
import com.URBinLAB.document.Document;
import com.URBinLAB.document.aerial_image.AerialImageRepository;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;
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
public class SatelliteImageService {

    private DocumentRepository documentRepository;
    private AerialImageRepository aerialImageRepository;
    private SatelliteImageRepository satelliteImageRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public SatelliteImageService(DocumentRepository documentRepository,
                                 AerialImageRepository aerialImageRepository,
                                 SatelliteImageRepository satelliteImageRepository,
                                 TokenRepository tokenRepository) {

        this.aerialImageRepository = aerialImageRepository;
        this.documentRepository = documentRepository;
        this.satelliteImageRepository = satelliteImageRepository;
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

    public ResponseEntity<String> createDocument(MultiValueMap<String,String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String satellite,
                                                 String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("SATELLITE IMAGE")
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

            SatelliteImage satelliteImage = SatelliteImage.builder()
                    .image(image)
                    .satellite(satellite)
                    .resolution(resolution)
                    .build();

            satelliteImage = this.satelliteImageRepository.save(satelliteImage);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllSatellite() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.satelliteImageRepository.getAllSatellite()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.satelliteImageRepository.getAllResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.satelliteImageRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}