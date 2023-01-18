package com.URBinLAB.document;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE \"document\"\n" +
            "SET collection_id = :col\n" +
            "WHERE document_id=:id", nativeQuery = true)
    @Transactional
    void changeCollection(@Param("id") Long id, @Param("col") Long collection);

    @Query(value = "SELECT MAX(d.document_id)\n" +
            "FROM \"document\" d \n", nativeQuery = true)
    Long getMaxId();

    @Query("SELECT d FROM Document d WHERE d.name = :name")
    List<Document> getOneByName(@Param("name") String name);

    @Query(value = "SELECT *\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    List<Object> getDocumentById(@Param("id") Long id);

    @Query(value = "select d.\"document_id\" as id, d.\"name\", d.\"clicks\", d.\"files\"\n" +
            "from \"document\" d \n" +
            "order by d.\"clicks\" desc"
            , nativeQuery = true)
    List<Object> getAllPaging(Pageable pageable);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "ORDER BY RANDOM()\n" +
            "LIMIT :limit" , nativeQuery = true)
    List<Object> getAll(@Param("limit") Long limit);

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

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "JOIN UNNEST(CAST(:array AS int[]))\n" +
            "WITH ORDINALITY t(document_id, ord)\n" +
            "USING (document_id)\n" +
            "WHERE d.document_id IN :list \n" +
            "ORDER BY t.ord;" , nativeQuery = true)
    List<Object> fromList(@Param("list") List<Integer> list, @Param("array") String array);

    @Query(value = "SELECT d.provider, COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "GROUP BY d.provider\n" +
            "ORDER BY d.provider", nativeQuery = true)
    List<Object> groupByProvider(@Param("list") List<Integer> list);

    @Query(value = "SELECT EXTRACT(YEAR FROM d.time_scope), COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "GROUP BY EXTRACT(YEAR FROM d.time_scope)\n" +
            "ORDER BY EXTRACT(YEAR FROM d.time_scope)", nativeQuery = true)
    List<Object> groupByYear(@Param("list") List<Integer> list);

    @Query(value = "SELECT d.type, COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "GROUP BY d.type\n" +
            "ORDER BY d.type", nativeQuery = true)
    List<Object> groupByType(@Param("list") List<Integer> list);

    @Query(value = "SELECT r.name, r.researcher_id, COUNT(d.document_id)\n" +
            "FROM \"researcher\" r\n" +
            "INNER JOIN \"document\" d\n" +
            "ON d.archiver_id = r.researcher_id\n" +
            "WHERE d.document_id IN :list\n" +
            "GROUP BY r.researcher_id\n" +
            "ORDER BY r.name", nativeQuery = true)
    List<Object> groupByArchiver(@Param("list") List<Integer> list);

    @Query(value = "SELECT ST_AsText(s.space), ST_AsText(ST_Centroid(s.space)) as center, s.space_id\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"space\" s \n" +
            "ON d.space_id = s.space_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    List<Object> getSpaceFromDocument(@Param("id") Long id);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "AND LOWER(d.name) LIKE %:name%", nativeQuery = true)
    List<Object> getDocumentByName(@Param("name") String name,
                                   @Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE LOWER(d.name) LIKE %:name%", nativeQuery = true)
    List<Object> getDocumentByName(@Param("name") String name);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "AND EXTRACT(YEAR FROM d.time_scope) IN :years\n" +
            "AND d.provider IN :providers\n" +
            "AND d.archiver_id IN :archivers\n" +
            "AND d.type IN :types", nativeQuery = true)
    List<Object> filter(@Param("years") List<Integer> years,
                        @Param("providers") List<String> providers,
                        @Param("archivers") List<Integer> archivers,
                        @Param("types") List<String> types,
                        @Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE EXTRACT(YEAR FROM d.time_scope) IN :years\n" +
            "AND d.provider IN :providers\n" +
            "AND d.archiver_id IN :archivers\n" +
            "AND d.type IN :types", nativeQuery = true)
    List<Object> filter(@Param("years") List<Integer> years,
                        @Param("providers") List<String> providers,
                        @Param("archivers") List<Integer> archivers,
                        @Param("types") List<String> types);

    @Query(value = "SELECT d.provider, COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "GROUP BY d.provider\n" +
            "ORDER BY d.provider", nativeQuery = true)
    List<Object> groupByProvider();

    @Query(value = "SELECT EXTRACT(YEAR FROM d.time_scope), COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "GROUP BY EXTRACT(YEAR FROM d.time_scope)\n" +
            "ORDER BY EXTRACT(YEAR FROM d.time_scope)", nativeQuery = true)
    List<Object> groupByYear();

    @Query(value = "SELECT d.type, COUNT(d.document_id)\n" +
            "FROM \"document\" d\n" +
            "GROUP BY d.type\n" +
            "ORDER BY d.type", nativeQuery = true)
    List<Object> groupByType();

    @Query(value = "SELECT r.name, r.researcher_id, COUNT(d.document_id)\n" +
            "FROM \"researcher\" r\n" +
            "INNER JOIN \"document\" d\n" +
            "ON d.archiver_id = r.researcher_id\n" +
            "GROUP BY r.researcher_id\n" +
            "ORDER BY r.name", nativeQuery = true)
    List<Object> groupByArchiver();

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "ORDER BY EXTRACT(YEAR FROM d.time_scope)\n" +
            "ASC", nativeQuery = true)
    List<Object> orderByYearAsc(@Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "ORDER BY EXTRACT(YEAR FROM d.time_scope)\n" +
            "DESC", nativeQuery = true)
    List<Object> orderByYearDesc(@Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "ORDER BY d.name\n" +
            "ASC", nativeQuery = true)
    List<Object> orderByNameAsc(@Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"document\" d\n" +
            "WHERE d.document_id IN :list\n" +
            "ORDER BY d.name\n" +
            "DESC", nativeQuery = true)
    List<Object> orderByNameDesc(@Param("list") List<Integer> list);
}
