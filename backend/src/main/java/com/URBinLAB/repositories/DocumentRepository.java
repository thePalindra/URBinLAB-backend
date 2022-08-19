package com.URBinLAB.repositories;

import com.URBinLAB.domains.Document;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE d.name = :name")
    List<Document> getOneByName(@Param("name") String name);

    @Query(value = "select d.\"document_id\" as id, d.\"name\", d.\"clicks\", d.\"files\"\n" +
            "from \"document\" d \n" +
            "order by d.\"clicks\" desc"
            , nativeQuery = true)
    List<Object> getAllPaging(Pageable pageable);

    @Query(value = "select d.\"document_id\" as id, d.\"name\", d.\"clicks\", d.\"files\"\n" +
            "from \"document\" d \n" +
            "where d.\"name\" like %:name% \n" +
            "order by d.\"clicks\" desc"
            , nativeQuery = true)
    List<Object> getDocumentByName(Pageable pageable, @Param("name") String name);


    @Query(value = "SELECT d.document_id, d.name, d.type, d.time_scope " +
            "FROM \"document\" d " +
            "WHERE d.space_id = :space"
            , nativeQuery = true)
    List<Object> getDocumentBySpaceId(Pageable pageable, @Param("space") Long space);

    @Query(value = "SELECT *\n" +
            "FROM (SELECT *\n" +
            "\tFROM \"document\" d) res\n" +
            "INNER JOIN (SELECT * \n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.name LIKE :mame%) byName \n" +
            "ON byName.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.provider LIKE :provider%) byProvider\n" +
            "ON byProvider.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.archiver_id>=:archiverMin " +
            "\tAND d.archiver_id<=:archiverMax) byArchiver\n" +
            "ON byArchiver.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE EXTRACT(YEAR FROM d.time_scope)>=:yearMin\n" +
            "\tand EXTRACT(YEAR FROM d.time_scope)<=:yearMax) byYear\n" +
            "ON byYear.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.type IN :types) byType\n" +
            "ON byType.document_id=res.document_id"
            , nativeQuery = true)
    List<Object> bigFormQuery(Pageable pageable,
                              @Param("name") String name,
                              @Param("provider") String provider,
                              @Param("archiverMax") Long archiverMax,
                              @Param("archiverMin") Long archiverMin,
                              @Param("yearMax") Long yearMax,
                              @Param("yearMin") Long yearMin,
                              @Param("types") Set<String> types);
}
