package com.URBinLAB.repositories;

import com.URBinLAB.domains.ThematicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThematicMapRepository extends JpaRepository<ThematicMap, Long> {
}
