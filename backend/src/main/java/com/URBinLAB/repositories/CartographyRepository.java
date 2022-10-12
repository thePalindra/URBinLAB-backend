package com.URBinLAB.repositories;

import com.URBinLAB.domains.Cartography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartographyRepository extends JpaRepository<Cartography, Long> {

    @Query(value = "SELECT DISTINCT c.image_resolution " +
            "FROM \"cartography\" c", nativeQuery = true)
    List<String> getAllImageResolution();

    @Query(value = "SELECT DISTINCT c.geometry_type " +
            "FROM \"cartography\" c", nativeQuery = true)
    List<String> getAllGeometryType();

    @Query(value = "SELECT DISTINCT c.scale " +
            "FROM \"cartography\" c", nativeQuery = true)
    List<String> getAllScale();
}
