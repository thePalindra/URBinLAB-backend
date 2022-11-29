package com.URBinLAB.document.aerial_image.aerial_photography;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
