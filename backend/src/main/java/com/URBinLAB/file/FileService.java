package com.URBinLAB.file;

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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class FileService {

    static final String VOLUME_PATH = "C:\\Users\\Palindra\\Desktop\\FCT-UNL\\5º Ano\\Tese\\2º Semestre\\Projeto\\backend\\URBinLAB-backend\\backend\\files\\";

    private FileRepository fileRepository;
    private DocumentRepository documentRepository;
    private TokenRepository tokenRepository;
    private HttpServletRequest request;
    private final Gson gson = new Gson();

    @Autowired
    public FileService(FileRepository fileRepository,
                       DocumentRepository documentRepository,
                       TokenRepository tokenRepository,
                       HttpServletRequest request) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.request = request;
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

    public ResponseEntity<String> attachFile(MultipartFile file,
                                             Long document) {

        try {
            Optional<Document> doc = this.documentRepository.findById(document);
            if (doc.isEmpty())
                return new ResponseEntity<>(new Gson().toJson("No document found!"), HttpStatus.NOT_FOUND);

            File saved = File.builder()
                    .name(file.getOriginalFilename())
                    .file(VOLUME_PATH + file.getOriginalFilename())
                    .document(doc.get())
                    .creationDate(new Date())
                    .format(file.getContentType())
                    .size(file.getSize())
                    .build();


            //System.out.println(request.getServletContext().getRealPath("/"));
            //file.transferTo(new java.io.File(VOLUME_PATH + file.getOriginalFilename()));
            this.fileRepository.save(saved);
            return new ResponseEntity<>(new Gson().toJson(file), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getFiles(Long id) {
        try {
            return new ResponseEntity<>(new Gson().toJson(this.fileRepository.getFiles(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteFile(Long id) {
        try{
            this.fileRepository.deleteById(id);
            return new ResponseEntity<>(new Gson().toJson("Deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.gson.toJson("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
    }
}