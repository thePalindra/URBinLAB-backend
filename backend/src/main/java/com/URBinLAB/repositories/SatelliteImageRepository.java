package com.URBinLAB.repositories;

import com.URBinLAB.domains.SatelliteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatelliteImageRepository extends JpaRepository<SatelliteImage, Long> {
}
