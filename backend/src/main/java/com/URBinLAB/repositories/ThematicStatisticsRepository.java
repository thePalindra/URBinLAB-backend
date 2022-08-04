package com.URBinLAB.repositories;

import com.URBinLAB.domains.ThematicStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThematicStatisticsRepository extends JpaRepository<ThematicStatistics, Long> {
}
