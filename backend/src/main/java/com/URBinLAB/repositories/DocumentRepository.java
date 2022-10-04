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

    @Query(value = "SELECT MAX(d.archiver_id) " +
            "FROM \"document\" d"
            , nativeQuery = true)
    Long getMaxArchiverId();

    @Query(value = "SELECT DISTINCT d.provider\n" +
            "FROM \"document\" d " +
            "ORDER BY d.provider", nativeQuery = true)
    List<String> getAllProviders();

    @Query(value = "SELECT DISTINCT d.link\n" +
            "FROM \"document\" d " +
            "ORDER BY d.link", nativeQuery = true)
    List<String> getAllURLs();

    @Query(value = "SELECT res.document_id, res.space_id, res.name, res.type, EXTRACT(YEAR FROM res.time_scope)\n" +
            "FROM (SELECT *\n" +
            "\tFROM \"document\" d) res\n" +
            "INNER JOIN (SELECT * \n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.name LIKE :name%) byName \n" +
            "ON byName.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.provider LIKE :provider%) byProvider\n" +
            "ON byProvider.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.archiver_id >= :minar " +
            "\tAND d.archiver_id <= :maxar) byArchiver\n" +
            "ON byArchiver.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE EXTRACT(YEAR FROM d.time_scope) >= CAST(CAST(:miny AS TEXT) AS integer)\n" +
            "\tand EXTRACT(YEAR FROM d.time_scope) <= CAST(CAST(:maxy AS TEXT) AS integer)) byYear\n" +
            "ON byYear.document_id=res.document_id\n" +
            "INNER JOIN (SELECT d.document_id\n" +
            "\tFROM \"document\" d\n" +
            "\tWHERE d.type IN :types) byType\n" +
            "ON byType.document_id=res.document_id"
            , nativeQuery = true)
    List<Object> bigFormQuery(@Param("name")String name,
                              @Param("provider")String provider,
                              @Param("maxar")Long archiverMax,
                              @Param("minar")Long archiverMin,
                              @Param("maxy")Integer yearMax,
                              @Param("miny")Integer yearMin,
                              @Param("types") List<String> types,
                              Pageable pageable);
}
