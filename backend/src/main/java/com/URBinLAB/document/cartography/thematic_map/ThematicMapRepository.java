package com.URBinLAB.document.cartography.thematic_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThematicMapRepository extends JpaRepository<ThematicMap, Long> {

    @Query(value = "SELECT DISTINCT tm.theme " +
            "FROM \"thematic_map\" tm " +
            "ORDER BY tm.theme", nativeQuery = true)
    List<String> getAllTheme();

    @Query(value = "SELECT DISTINCT tm.thematic_map_type " +
            "FROM \"thematic_map\" tm " +
            "ORDER BY tm.thematic_map_type", nativeQuery = true)
    List<String> getAllThematicMapType();

    @Query(value = "SELECT car.scale, car.raster, car.image_resolution, car.geometry_type, tm.theme, tm.thematic_map_type\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"cartography\" car\n" +
            "ON car.document_id = d.document_id\n" +
            "INNER JOIN \"thematic_map\" tm\n" +
            "ON tm.cartography_id = car.cartography_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
