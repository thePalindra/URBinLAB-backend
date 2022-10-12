package com.URBinLAB.repositories;

import com.URBinLAB.domains.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query(value = "SELECT DISTINCT s.theme " +
            "FROM \"statistics\" s", nativeQuery = true)
    List<String> getAllThemes();
}
