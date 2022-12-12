package com.URBinLAB.document.aerial_image.aerial_photography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AerialPhotographyRepository extends JpaRepository<AerialPhotography, Long> {

    @Query(value = "SELECT DISTINCT ap.approximate_scale " +
            "FROM \"aerial_photography\" ap " +
            "ORDER BY ap.approximate_scale", nativeQuery = true)
    List<String> getAllScale();

    @Query(value = "SELECT DISTINCT ap.image_resolution " +
            "FROM \"aerial_photography\" ap " +
            "ORDER BY ap.image_resolution", nativeQuery = true)
    List<String> getAllImageResolution();

    @Query(value = "SELECT ap.image_resolution, ap.approximate_scale\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"aerial_image\" ai\n" +
            "ON ai.document_id = d.document_id\n" +
            "INNER JOIN \"aerial_photography\" ap\n" +
            "ON ap.aerial_image_id = ai.aerial_image_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
