package com.URBinLAB.document.aerial_image.ortos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrtosRepository extends JpaRepository<Ortos, Long> {

    @Query(value = "SELECT DISTINCT o.resolution " +
            "FROM \"ortos\" o " +
            "ORDER BY o.resolution", nativeQuery = true)
    List<String> getAllResolution();

    @Query(value = "SELECT DISTINCT o.scale " +
            "FROM \"ortos\" o " +
            "ORDER BY o.scale", nativeQuery = true)
    List<String> getAllScale();

    @Query(value = "SELECT o.resolution, o.scale\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"aerial_image\" ai\n" +
            "ON ai.document_id = d.document_id\n" +
            "INNER JOIN \"ortos\" o\n" +
            "ON o.aerial_image_id = ai.aerial_image_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
