package com.URBinLAB.cartography.base_map.chorographic_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChorographicMapRepository extends JpaRepository<ChorographicMap, Long> {
}
