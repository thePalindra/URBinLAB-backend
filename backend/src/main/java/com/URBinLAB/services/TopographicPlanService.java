package com.URBinLAB.services;

import com.URBinLAB.domains.*;
import com.URBinLAB.repositories.*;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.swing.plaf.basic.BasicTreeUI;
import java.util.Date;

@Service
public class TopographicPlanService {

    private DocumentRepository documentRepository;
    private CartographyRepository cartographyRepository;
    private BaseMapRepository baseMapRepository;
    private TopographicPlanRepository topographicPlanRepository;
    private TokenRepository tokenRepository;
    private final Gson gson = new Gson();

    @Autowired
    public TopographicPlanService(DocumentRepository documentRepository,
                                  CartographyRepository cartographyRepository,
                                  BaseMapRepository baseMapRepository,
                                  TopographicPlanRepository topographicPlanRepository,
                                  TokenRepository tokenRepository) {

        this.documentRepository = documentRepository;
        this.cartographyRepository = cartographyRepository;
        this.baseMapRepository = baseMapRepository;
        this.topographicPlanRepository = topographicPlanRepository;
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

    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link,
                                                 Integer scale,
                                                 Boolean raster,
                                                 String resolution,
                                                 String type) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("TOPOGRAPHICPLAN")
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

            TopographicPlan topographicPlan = TopographicPlan.builder()
                    .baseMap(baseMap)
                    .build();

            topographicPlan = this.topographicPlanRepository.save(topographicPlan);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
