package com.URBinLAB.space;

import com.URBinLAB.document.Document;
import com.URBinLAB.researcher.Researcher;
import com.URBinLAB.token.Token;
import com.URBinLAB.document.DocumentRepository;
import com.URBinLAB.token.TokenRepository;
import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    private SpaceRepository spaceRepository;
    private DocumentRepository documentRepository;
    private final Gson gson = new Gson();

    @Autowired
    public SpaceService(SpaceRepository spaceRepository,
                        DocumentRepository documentRepository) {

        this.spaceRepository = spaceRepository;
        this.documentRepository = documentRepository;
    }

    public ResponseEntity<String> attachSpace(Long id,
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

            return new ResponseEntity<>(new Gson().toJson(doc.get().getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addSpace(Long document,
                                           String space) {
        try {

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            Document temp = doc.get();

            this.spaceRepository.insertWKT(this.spaceRepository.getMax() + 1, temp.getName(), space);

            temp.setSpace(this.spaceRepository.getById(this.spaceRepository.getMax()));
            this.documentRepository.save(temp);

            return new ResponseEntity<>(new Gson().toJson(temp.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addCircle(Long document,
                                            Double lng,
                                            Double lat,
                                            Double size) {
        try {

            System.out.println(size);

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            Document temp = doc.get();

            this.spaceRepository.insertCircle(this.spaceRepository.getMax() + 1, temp.getName(), lng, lat, size);

            temp.setSpace(this.spaceRepository.getById(this.spaceRepository.getMax()));
            this.documentRepository.save(temp);

            return new ResponseEntity<>(new Gson().toJson(temp.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addFileSpace(Long document,
                                               String geojson,
                                               String name) {
        try {
            System.out.println(geojson);

            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.BAD_REQUEST);

            Document temp = doc.get();

            Long max = 0l;
            if (this.spaceRepository.getMax()!=null)
                max = this.spaceRepository.getMax();

            this.spaceRepository.insertGeoJson(max + 1, name, geojson);

            temp.setSpace(this.spaceRepository.getById(this.spaceRepository.getMax()));
            this.documentRepository.save(temp);

            return new ResponseEntity<>(new Gson().toJson(temp.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllFromLevel(Integer level) {
        try {
            List<Object> spaces = this.spaceRepository.getAllFromLevel(level);

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> searchByName(String name,
                                               String level,
                                               String hierarchy) {
        try {

            List<Object> spaces = this.spaceRepository.searchByName(name, level, hierarchy);

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllHierarchyTypes() {
        try {

            List<Object> spaces = this.spaceRepository.getAllHierarchyTypes();

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllHierarchies(String type) {

            List<Object> spaces = this.spaceRepository.getAllHierarchies(type);

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);

    }

    public ResponseEntity<String> getAllLevels(String hierarchy) {
        try {

            List<Object> levels = this.spaceRepository.getAllLevels(hierarchy);

            return new ResponseEntity<>(new Gson().toJson(levels), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllNamesFromLevel(String hierarchy,
                                                       String level) {
        try {

            List<Object> spaces = this.spaceRepository.getAllNamesFromLevel(hierarchy, level);

            return new ResponseEntity<>(new Gson().toJson(spaces), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllSpaces() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.spaceRepository.getAllSpaces()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}
