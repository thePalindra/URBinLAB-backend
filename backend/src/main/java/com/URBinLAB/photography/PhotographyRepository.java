package com.URBinLAB.photography;

import com.URBinLAB.photography.Photography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographyRepository extends JpaRepository<Photography, Long> {

    @Query(value = "SELECT DISTINCT p.image_resolution " +
            "FROM \"photography\" p " +
            "ORDER BY p.image_resolution", nativeQuery = true)
    List<String> getAllImageResolution();
}
