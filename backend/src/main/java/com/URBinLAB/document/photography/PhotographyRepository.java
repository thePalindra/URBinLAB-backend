package com.URBinLAB.document.photography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographyRepository extends JpaRepository<Photography, Long> {

    @Query(value = "SELECT DISTINCT p.image_resolution " +
            "FROM \"photography\" p " +
            "ORDER BY p.image_resolution", nativeQuery = true)
    List<String> getAllImageResolution();

    @Query(value = "SELECT ph.image_resolution\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"photography\" ph\n" +
            "ON ph.document_id = d.document_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
