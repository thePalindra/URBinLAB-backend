package com.URBinLAB.repositories;

import com.URBinLAB.domains.GeographicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeographicMapRepository extends JpaRepository<GeographicMap, Long> {
}
