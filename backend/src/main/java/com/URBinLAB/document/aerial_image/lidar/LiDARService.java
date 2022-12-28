package com.URBinLAB.document.aerial_image.lidar;

import com.URBinLAB.document.aerial_image.AerialImage;
import com.URBinLAB.document.aerial_image.AerialImageRepository;
import com.URBinLAB.document.Document;
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
public class LiDARService {

    private DocumentRepository documentRepository;
    private AerialImageRepository aerialImageRepository;
    private LiDARRepository liDARRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public LiDARService(DocumentRepository documentRepository,
                        AerialImageRepository aerialImageRepository,
                        LiDARRepository liDARRepository,
                        TokenRepository tokenRepository) {

        this.aerialImageRepository = aerialImageRepository;
        this.documentRepository = documentRepository;
        this.liDARRepository = liDARRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> createDocument(MultiValueMap<String,String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("LiDAR")
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

            LiDAR liDAR = LiDAR.builder()
                    .image(image)
                    .resolution(resolution)
                    .build();

            liDAR = this.liDARRepository.save(liDAR);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.liDARRepository.getAllResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.liDARRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
