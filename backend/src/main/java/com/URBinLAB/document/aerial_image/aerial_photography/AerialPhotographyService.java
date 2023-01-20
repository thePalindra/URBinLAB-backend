package com.URBinLAB.document.aerial_image.aerial_photography;

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

    public ResponseEntity<String> createAerialPhotography(String token,
                                                          String name,
                                                          String description,
                                                          String provider,
                                                          Date timeScope,
                                                          String link,
                                                          String scale,
                                                          String resolution) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("AERIAL PHOTOS")
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

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllScale() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.aerialPhotographyRepository.getAllScale()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllImageResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.aerialPhotographyRepository.getAllImageResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.aerialPhotographyRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
