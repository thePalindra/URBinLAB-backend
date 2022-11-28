package com.URBinLAB.cartography.base_map.geographic_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeographicMapRepository extends JpaRepository<GeographicMap, Long> {
}
