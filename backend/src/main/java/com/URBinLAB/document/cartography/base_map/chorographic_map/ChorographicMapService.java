package com.URBinLAB.document.cartography.base_map.chorographic_map;

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
public class ChorographicMapService {

    private DocumentRepository documentRepository;
    private CartographyRepository cartographyRepository;
    private BaseMapRepository baseMapRepository;
    private ChorographicMapRepository chorographicMapRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public ChorographicMapService(DocumentRepository documentRepository,
                                  CartographyRepository cartographyRepository,
                                  BaseMapRepository baseMapRepository,
                                  ChorographicMapRepository chorographicMapRepository,
                                  TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.cartographyRepository = cartographyRepository;
        this.baseMapRepository = baseMapRepository;
        this.chorographicMapRepository = chorographicMapRepository;
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

            Long id = this.documentRepository.getMaxId();

            if (id == null)
                id = 0l;

            System.out.println(id.toString());

            Document document = Document.builder()
                    .id(id+1)
                    .archiver(temp.getResearcher())
                    .type("CHOROGRAPHIC MAPS")
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

            ChorographicMap chorographicMap = ChorographicMap.builder()
                    .baseMap(baseMap)
                    .build();

            chorographicMap = this.chorographicMapRepository.save(chorographicMap);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
