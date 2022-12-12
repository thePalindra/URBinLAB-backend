package com.URBinLAB.document.drawings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawingsRepository extends JpaRepository<Drawings, Long> {

    @Query(value = "SELECT DISTINCT d.context " +
            "FROM \"drawings\" d " +
            "ORDER BY d.context", nativeQuery = true)
    List<String> getAllContext();

    @Query(value = "SELECT dr.context\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"drawings\" dr\n" +
            "ON d.document_id = dr.document_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
