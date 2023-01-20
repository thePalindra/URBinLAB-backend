package com.URBinLAB.document.cartography.base_map.geographic_map;

import com.URBinLAB.document.cartography.base_map.BaseMap;
import com.URBinLAB.document.cartography.base_map.BaseMapRepository;
import com.URBinLAB.document.Document;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.document.cartography.Cartography;
import com.URBinLAB.document.cartography.CartographyRepository;
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
public class GeographicMapService {

    private DocumentRepository documentRepository;
    private CartographyRepository cartographyRepository;
    private BaseMapRepository baseMapRepository;
    private GeographicMapRepository geographicMapRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public GeographicMapService(DocumentRepository documentRepository,
                                CartographyRepository cartographyRepository,
                                BaseMapRepository baseMapRepository,
                                GeographicMapRepository geographicMapRepository,
                                TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.cartographyRepository = cartographyRepository;
        this.baseMapRepository = baseMapRepository;
        this.geographicMapRepository = geographicMapRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> createDocument(String token,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 String scale,
                                                 Boolean raster,
                                                 String resolution,
                                                 String type) {
        try {
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("GEOGRAPHIC MAPS")
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

            BaseMap baseMap = BaseMap.builder()
                    .cartography(cartography)
                    .build();

            baseMap = this.baseMapRepository.save(baseMap);

            GeographicMap geographicMap = GeographicMap.builder()
                    .baseMap(baseMap)
                    .build();

            geographicMap = this.geographicMapRepository.save(geographicMap);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllScale() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.cartographyRepository.getAllScale()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllImageResolution() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.cartographyRepository.getAllImageResolution()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllGeometryType() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.cartographyRepository.getAllGeometryType()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.cartographyRepository.getByDocId(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
