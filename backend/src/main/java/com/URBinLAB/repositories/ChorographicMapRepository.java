package com.URBinLAB.repositories;

import com.URBinLAB.domains.ChorographicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChorographicMapRepository extends JpaRepository<ChorographicMap, Long> {
}
