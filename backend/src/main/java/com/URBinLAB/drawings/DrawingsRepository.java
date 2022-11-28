package com.URBinLAB.drawings;

import com.URBinLAB.drawings.Drawings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawingsRepository extends JpaRepository<Drawings, Long> {

    @Query(value = "SELECT DISTINCT d.context " +
            "FROM \"drawings\" d " +
            "ORDER BY d.context", nativeQuery = true)
    List<String> getAllContext();
}
