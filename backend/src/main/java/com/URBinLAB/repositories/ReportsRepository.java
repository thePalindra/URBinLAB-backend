package com.URBinLAB.repositories;

import com.URBinLAB.domains.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {

    @Query(value = "SELECT DISTINCT r.context " +
            "FROM \"reports\" r", nativeQuery = true)
    List<String> getAllContext();

    @Query(value = "SELECT DISTINCT r.theme " +
            "FROM \"reports\" r", nativeQuery = true)
    List<String> getAllTheme();
}
