package com.URBinLAB.repositories;

import com.URBinLAB.domains.AerialImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerialImageRepository extends JpaRepository<AerialImage, Long> {
}
