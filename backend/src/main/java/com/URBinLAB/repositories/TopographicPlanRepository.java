package com.URBinLAB.repositories;

import com.URBinLAB.domains.TopographicPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopographicPlanRepository extends JpaRepository<TopographicPlan, Long> {
}
