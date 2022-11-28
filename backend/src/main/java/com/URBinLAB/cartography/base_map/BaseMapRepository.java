package com.URBinLAB.cartography.base_map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMapRepository extends JpaRepository<BaseMap, Long> {
}
