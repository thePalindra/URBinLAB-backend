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
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
import java.util.Optional;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private CollectionRepository collectionRepository;
    private FileRepository fileRepository;
    private TokenRepository tokenRepository;
    private SpaceRepository spaceRepository;
    private AerialImageRepository aerialImageRepository;
    private AerialPhotographyRepository aerialPhotographyRepository;
    private PhotographyRepository photographyRepository;
    private CartographyRepository cartographyRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           CollectionRepository collectionRepository,
                           FileRepository fileRepository,
                           TokenRepository tokenRepository,
                           SpaceRepository spaceRepository,
                           AerialImageRepository aerialImageRepository,
                           AerialPhotographyRepository aerialPhotographyRepository,
                           PhotographyRepository photographyRepository,
                           CartographyRepository cartographyRepository) {

        this.documentRepository = documentRepository;
        this.collectionRepository = collectionRepository;
        this.fileRepository = fileRepository;
        this.tokenRepository = tokenRepository;
        this.spaceRepository = spaceRepository;
        this.aerialImageRepository = aerialImageRepository;
        this.aerialPhotographyRepository = aerialPhotographyRepository;
        this.photographyRepository = photographyRepository;
        this.cartographyRepository = cartographyRepository;
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

    public ResponseEntity<String> addDocument(MultiValueMap<String,
                                              String> map,
                                              Long collectionId,
                                              String name,
                                              String description,
                                              String type,
                                              String provider,
                                              Date timeScope,
                                              String link) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Optional<Collection> collection = this.collectionRepository.findById(collectionId);
            if (collection.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection.get())
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            return new ResponseEntity<>(new Gson().toJson(document), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createAerialImage(MultiValueMap<String,
                                                    String> map,
                                                    Long collectionId,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Optional<Collection> collection = this.collectionRepository.findById(collectionId);
            if (collection.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection.get())
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            AerialImage aerialImage = AerialImage.builder()
                    .document(document)
                    .approximateScale(scale)
                    .build();

            aerialImage = this.aerialImageRepository.save(aerialImage);

            return new ResponseEntity<>(new Gson().toJson(aerialImage), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createAerialPhotography(MultiValueMap<String,
            String> map,
                                                    Long collectionId,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Optional<Collection> collection = this.collectionRepository.findById(collectionId);
            if (collection.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection.get())
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            AerialImage aerialImage = AerialImage.builder()
                    .document(document)
                    .approximateScale(scale)
                    .build();

            aerialImage = this.aerialImageRepository.save(aerialImage);

            AerialPhotography aerialPhotography = AerialPhotography.builder()
                    .aerialImage(aerialImage)
                    .resolution(resolution)
                    .build();

            aerialPhotography = this.aerialPhotographyRepository.save(aerialPhotography);

            return new ResponseEntity<>(new Gson().toJson(aerialPhotography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createPhotography(MultiValueMap<String,
            String> map,
                                                          Long collectionId,
                                                          String name,
                                                          String description,
                                                          String type,
                                                          String provider,
                                                          Date timeScope,
                                                          String link,
                                                          String resolution) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Optional<Collection> collection = this.collectionRepository.findById(collectionId);
            if (collection.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection.get())
                    .archiver(temp.getResearcher())
                    .type(type)
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            Photography photography = Photography.builder()
                    .document(document)
                    .resolution(resolution)
                    .build();

            photography = this.photographyRepository.save(photography);

            return new ResponseEntity<>(new Gson().toJson(photography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createCartography(MultiValueMap<String,
                                                    String> map,
                                                    Long collectionId,
                                                    String name,
                                                    String description,
                                                    String type,
                                                    String provider,
                                                    Date timeScope,
                                                    String link,
                                                    Integer scale,
                                                    String format) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);
            Optional<Collection> collection = this.collectionRepository.findById(collectionId);
            if (collection.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No collection found!"), HttpStatus.BAD_REQUEST);

            Document document = Document.builder()
                    .collection(collection.get())
                    .archiver(temp.getResearcher())
                    .type(type)
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
                    .format(format)
                    .build();

            cartography = this.cartographyRepository.save(cartography);

            return new ResponseEntity<>(new Gson().toJson(cartography), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document,
                                             String name,
                                             String format,
                                             Date creation,
                                             Long size) {
        try {

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            File saved = File.builder()
                    .name(name)
                    .file(file.getBytes())
                    .document(doc.get())
                    .creationDate(creation)
                    .format(format)
                    .size(size)
                    .build();
            this.fileRepository.save(saved);
            return new ResponseEntity<>(new Gson().toJson(size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> setSpace(Long id,
                                           Long document) {
        try {

            Optional<Space> space = this.spaceRepository.findById(id);
            if (space.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No space found!"), HttpStatus.BAD_REQUEST);

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            doc.get().setSpace(space.get());
            this.documentRepository.save(doc.get());

            return new ResponseEntity<>(new Gson().toJson(space.get()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}