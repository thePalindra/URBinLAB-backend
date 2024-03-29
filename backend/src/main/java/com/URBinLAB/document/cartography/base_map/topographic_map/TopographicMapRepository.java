package com.URBinLAB.document.cartography.base_map.topographic_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopographicMapRepository extends JpaRepository<TopographicMap, Long> {
}
