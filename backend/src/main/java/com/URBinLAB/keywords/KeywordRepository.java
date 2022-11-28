package com.URBinLAB.keywords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Modifying
    @Query(value = "INSERT INTO \"document_keyword\" (document_id, keyword_id)\n" +
            "VALUES (:document, :keyword)", nativeQuery = true)
    @Transactional
    void addKeywordToDocument(@Param("document") Long document, @Param("keyword") Long keyword);

    @Query(value = "SELECT *\n" +
            "FROM \"keyword\" k\n" +
            "WHERE k.keyword = :keyword", nativeQuery = true)
    List<Object> getKeywordByKeyword(@Param("keyword") String keyword);


}
