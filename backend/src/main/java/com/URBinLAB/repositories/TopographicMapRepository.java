package com.URBinLAB.repositories;

import com.URBinLAB.domains.TopographicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopographicMapRepository extends JpaRepository<TopographicMap, Long> {
}
