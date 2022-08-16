package com.URBinLAB.repositories;

import com.URBinLAB.domains.Document;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
