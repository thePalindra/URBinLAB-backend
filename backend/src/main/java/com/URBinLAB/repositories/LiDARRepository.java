package com.URBinLAB.repositories;

import com.URBinLAB.domains.LiDAR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiDARRepository extends JpaRepository<LiDAR, Long> {
}
