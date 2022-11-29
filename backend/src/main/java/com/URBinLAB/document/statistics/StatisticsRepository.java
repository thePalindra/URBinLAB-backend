package com.URBinLAB.document.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query(value = "SELECT DISTINCT s.theme " +
            "FROM \"statistics\" s " +
            "ORDER BY s.theme", nativeQuery = true)
    List<String> getAllThemes();
}
