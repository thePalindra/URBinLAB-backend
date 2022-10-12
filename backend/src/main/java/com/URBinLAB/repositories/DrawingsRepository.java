package com.URBinLAB.repositories;

import com.URBinLAB.domains.Drawings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawingsRepository extends JpaRepository<Drawings, Long> {

    @Query(value = "SELECT d.context " +
            "FROM \"drawings\" d", nativeQuery = true)
    List<String> getAllContext();
}
