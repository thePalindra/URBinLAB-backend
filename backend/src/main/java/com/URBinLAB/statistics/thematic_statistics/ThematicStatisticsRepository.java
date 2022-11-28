package com.URBinLAB.statistics.thematic_statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThematicStatisticsRepository extends JpaRepository<ThematicStatistics, Long> {
}
