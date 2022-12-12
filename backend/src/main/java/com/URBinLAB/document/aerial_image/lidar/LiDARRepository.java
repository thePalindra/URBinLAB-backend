package com.URBinLAB.document.aerial_image.lidar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiDARRepository extends JpaRepository<LiDAR, Long> {

    @Query(value = "SELECT DISTINCT l.resolution " +
            "FROM \"lidar\" l " +
            "ORDER BY l.resolution", nativeQuery = true)
    List<String> getAllResolution();

    @Query(value = "SELECT li.resolution\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"aerial_image\" ai\n" +
            "ON ai.document_id = d.document_id\n" +
            "INNER JOIN \"lidar\" li\n" +
            "ON li.aerial_image_id = ai.aerial_image_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
