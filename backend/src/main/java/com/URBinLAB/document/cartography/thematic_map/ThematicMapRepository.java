package com.URBinLAB.document.cartography.thematic_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
