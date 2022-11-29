package com.URBinLAB.document.cartography.base_map.topographic_plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopographicPlanRepository extends JpaRepository<TopographicPlan, Long> {
}
