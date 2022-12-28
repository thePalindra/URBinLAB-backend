package com.URBinLAB.document.cartography.base_map.topographic_map;

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
public class TopographicMapService {

    private DocumentRepository documentRepository;
    private CartographyRepository cartographyRepository;
    private BaseMapRepository baseMapRepository;
    private TopographicMapRepository topographicMapRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public TopographicMapService(DocumentRepository documentRepository,
                                  CartographyRepository cartographyRepository,
                                  BaseMapRepository baseMapRepository,
                                  TopographicMapRepository topographicMapRepository,
                                  TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.cartographyRepository = cartographyRepository;
        this.baseMapRepository = baseMapRepository;
        this.topographicMapRepository = topographicMapRepository;
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
                                                 String type) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("TOPOGRAPHIC MAP")
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

            TopographicMap topographicMap = TopographicMap.builder()
                    .baseMap(baseMap)
                    .build();

            topographicMap = this.topographicMapRepository.save(topographicMap);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
