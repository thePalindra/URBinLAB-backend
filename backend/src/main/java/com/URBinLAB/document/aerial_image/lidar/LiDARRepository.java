package com.URBinLAB.document.aerial_image.lidar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiDARRepository extends JpaRepository<LiDAR, Long> {

    @Query(value = "SELECT DISTINCT l.resolution " +
            "FROM \"lidar\" l " +
            "ORDER BY l.resolution", nativeQuery = true)
    List<String> getAllResolution();
}
