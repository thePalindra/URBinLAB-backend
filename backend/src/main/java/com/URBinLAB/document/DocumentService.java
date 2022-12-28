package com.URBinLAB.document;

import com.URBinLAB.collection.CollectionRepository;
import com.URBinLAB.space.SpaceRepository;
import com.URBinLAB.token.Token;
import com.URBinLAB.token.TokenRepository;

import com.URBinLAB.utils.AccessControl;
import com.URBinLAB.utils.Feature;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private SpaceRepository spaceRepository;
    private CollectionRepository collectionRepository;
    private final Gson gson = new Gson();

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           TokenRepository tokenRepository,
                           SpaceRepository spaceRepository,
                           CollectionRepository collectionRepository) {

        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.spaceRepository = spaceRepository;
        this.collectionRepository = collectionRepository;
    }

    public ResponseEntity<String> createDocument(MultiValueMap<String, String> map,
                                                 String name,
                                                 String description,
                                                 String provider,
                                                 Date timeScope,
                                                 String link) {
        try {

            String token = map.get("token").toString();
            token = token.substring(1, token.length() - 1);
            Token temp = gson.fromJson(token, Token.class);

            Document document = Document.builder()
                    .archiver(temp.getResearcher())
                    .type("GENERIC")
                    .description(description)
                    .provider(provider)
                    .timeScope(timeScope)
                    .link(link)
                    .name(name)
                    .creation(new Date())
                    .build();

            document = this.documentRepository.save(document);

            return new ResponseEntity<>(new Gson().toJson(document.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAll(Long limit) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getAll(limit)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteDocument(Long id) {
        try {
            this.documentRepository.deleteById(id);
            return new ResponseEntity<>(new Gson().toJson("Deleted successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getDocumentBySpaceId(Long id,
                                                       Integer page) {

        Pageable element = PageRequest.of(page, 10);

        List<List<Object>> list = new ArrayList<>();
        list.add(this.documentRepository.getDocumentBySpaceId(element,id));
        list.add(this.spaceRepository.getAllTheDocuments(element, id));

        return new ResponseEntity<>(new Gson().toJson(list), HttpStatus.OK);
    }

    public ResponseEntity<String> getDocumentBySpaceGeometry(String space,
                                                             Integer page) {

        return new ResponseEntity<>(new Gson().toJson(this.spaceRepository.getAllTheDocumentsByGeometry(space)), HttpStatus.OK);
    }

    public ResponseEntity<String> getDocumentBySpaceMarker(String space,
                                                           Integer page) {

        return new ResponseEntity<>(new Gson().toJson(this.spaceRepository.getAllTheDocumentsByMarker(space)), HttpStatus.OK);
    }

    public ResponseEntity<String> getDocumentBySpaceCircle(Double lng,
                                                           Double lat,
                                                           Double size,
                                                           Integer page) {

        return new ResponseEntity<>(new Gson().toJson(this.spaceRepository.getAllTheDocumentsByCircle(lng, lat, size)), HttpStatus.OK);
    }

    public ResponseEntity<String> getElementsFromQuery(String name,
                                                       String provider,
                                                       Long archiver,
                                                       Integer maxYear,
                                                       Integer minYear,
                                                       String[] types,
                                                       Integer page) {

        Long minArch = new Long(0);
        if (archiver==0)
            archiver = this.documentRepository.getMaxArchiverId();
        else
            minArch = archiver;
        Pageable element = PageRequest.of(page, 20);


        return new ResponseEntity<>(new Gson().toJson(this.documentRepository.bigFormQuery(name, provider, archiver, minArch, maxYear, minYear, Arrays.asList(types), element)), HttpStatus.OK);
    }

    public ResponseEntity<String> getFromList(Integer[] list) {
        try {
            String array = Arrays.toString(list);
            array = array.replaceFirst("\\[", "{").replaceFirst("]", "}");
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.fromList(Arrays.asList(list), array)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllProviders() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getAllProviders()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getAllURLs() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getAllURLs()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getSpaceFromDocument(Long id) {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getSpaceFromDocument(id)), HttpStatus.OK);

    }

    public ResponseEntity<String> getDocumentByName(String name, Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getDocumentByName(name.toLowerCase(), List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByProvider(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByProvider(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByYear() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByYear()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByType() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByType()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByArchiver() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByArchiver()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByProvider() {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByProvider()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByYear(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByYear(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByType(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByType(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> groupByArchiver(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.groupByArchiver(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> filter(Integer[] years, String[] providers, Integer[] archivers, String[] types, Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.filter(
                    List.of(years),
                    List.of(providers),
                    List.of(archivers),
                    List.of(types),
                    List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> filter(Integer[] years, String[] providers, Integer[] archivers, String[] types) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.filter(
                    List.of(years),
                    List.of(providers),
                    List.of(archivers),
                    List.of(types))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getDocumentByName(String name) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getDocumentByName(name.toLowerCase())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> orderByYearAsc(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.orderByYearAsc(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> orderByYearDesc(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.orderByYearDesc(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> orderByNameAsc(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.orderByNameAsc(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> orderByNameDesc(Integer[] list) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.orderByNameDesc(List.of(list))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getDocumentById(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.documentRepository.getDocumentById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addCollection(Long id, Long collection) {
        try {
            this.documentRepository.changeCollection(id, collection);

            return new ResponseEntity<>(new Gson().toJson("OK"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }
}