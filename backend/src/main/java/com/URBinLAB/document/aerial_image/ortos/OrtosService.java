package com.URBinLAB.document.aerial_image.ortos;

import com.URBinLAB.document.aerial_image.AerialImage;
import com.URBinLAB.document.Document;
import com.URBinLAB.document.aerial_image.AerialImageRepository;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;

@Service
public class OrtosService {

    private DocumentRepository documentRepository;
    private AerialImageRepository aerialImageRepository;
    private OrtosRepository ortosRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    public OrtosService(DocumentRepository documentRepository,
                        AerialImageRepository aerialImageRepository,
                        OrtosRepository ortosRepository,
                        TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.aerialImageRepository = aerialImageRepository;
        this.ortosRepository = ortosRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> createDocument(String token,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String scale,
                                                 String resolution) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Long id = this.documentRepository.getMaxId();

            if (id == null)
                id = 0l;

            System.out.println(id.toString());

            Document document = Document.builder()
                    .id(id+1)
                    .archiver(temp.getResearcher())
                    .type("ORTOS")
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

            Ortos ortos = Ortos.builder()
                    .image(image)
                    .scale(scale)
                    .resolution(resolution)
                    .build();

            ortos = this.ortosRepository.save(ortos);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllScale() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.ortosRepository.getAllScale()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.ortosRepository.getAllResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.ortosRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
