package com.URBinLAB.repositories;

import com.URBinLAB.domains.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, Long> {
}
