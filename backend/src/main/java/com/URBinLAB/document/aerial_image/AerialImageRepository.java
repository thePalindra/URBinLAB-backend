package com.URBinLAB.document.aerial_image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerialImageRepository extends JpaRepository<AerialImage, Long> {
}
