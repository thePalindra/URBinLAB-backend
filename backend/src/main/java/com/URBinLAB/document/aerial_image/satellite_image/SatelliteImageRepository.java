package com.URBinLAB.document.aerial_image.satellite_image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SatelliteImageRepository extends JpaRepository<SatelliteImage, Long> {

    @Query(value = "SELECT DISTINCT si.satellite " +
            "FROM \"satellite_image\" si " +
            "ORDER BY si.satellite", nativeQuery = true)
    List<String> getAllSatellite();

    @Query(value = "SELECT DISTINCT si.resolution " +
            "FROM \"satellite_image\" si " +
            "ORDER BY si.resolution", nativeQuery = true)
    List<String> getAllResolution();

    @Query(value = "SELECT st.satellite, st.resolution\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"aerial_image\" ai\n" +
            "ON ai.document_id = d.document_id\n" +
            "INNER JOIN \"satellite_image\" st\n" +
            "ON st.aerial_image_id = ai.aerial_image_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
