package com.URBinLAB.document.cartography.thematic_map;

import com.URBinLAB.document.cartography.Cartography;
import com.URBinLAB.document.cartography.CartographyRepository;
import com.URBinLAB.document.Document;
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
public class ThematicMapService {

    private DocumentRepository documentRepository;
    private CartographyRepository cartographyRepository;
    private ThematicMapRepository thematicMapRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    public ThematicMapService(DocumentRepository documentRepository,
                        CartographyRepository cartographyRepository,
                        ThematicMapRepository thematicMapRepository,
                        TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.cartographyRepository = cartographyRepository;
        this.thematicMapRepository = thematicMapRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String scale,
                                                 Boolean raster,
                                                 String resolution,
                                                 String type,
                                                 String theme,
                                                 String mapType) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("THEMATIC MAP")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Cartography cartography = Cartography.builder()
                    .document(document)
                    .scale(scale)
                    .resolution(resolution)
                    .raster(raster)
                    .type(type)
                    .build();

            cartography = this.cartographyRepository.save(cartography);

            ThematicMap thematicMap = ThematicMap.builder()
                    .cartography(cartography)
                    .theme(theme)
                    .type(mapType)
                    .build();

            thematicMap = this.thematicMapRepository.save(thematicMap);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllTheme() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.thematicMapRepository.getAllTheme()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllThematicMapType() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.thematicMapRepository.getAllThematicMapType()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.thematicMapRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
