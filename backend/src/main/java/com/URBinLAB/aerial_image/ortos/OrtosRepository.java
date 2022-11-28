package com.URBinLAB.aerial_image.ortos;

import com.URBinLAB.aerial_image.ortos.Ortos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrtosRepository extends JpaRepository<Ortos, Long> {

    @Query(value = "SELECT DISTINCT o.resolution " +
            "FROM \"ortos\" o " +
            "ORDER BY o.resolution", nativeQuery = true)
    List<String> getAllResolution();

    @Query(value = "SELECT DISTINCT o.scale " +
            "FROM \"ortos\" o " +
            "ORDER BY o.scale", nativeQuery = true)
    List<String> getAllScale();
}
