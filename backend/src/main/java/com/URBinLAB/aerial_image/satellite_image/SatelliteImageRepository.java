package com.URBinLAB.aerial_image.satellite_image;

import com.URBinLAB.aerial_image.satellite_image.SatelliteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
