package com.URBinLAB.repositories;

import com.URBinLAB.domains.AerialPhotography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerialPhotographyRepository extends JpaRepository<AerialPhotography, Long> {
}
