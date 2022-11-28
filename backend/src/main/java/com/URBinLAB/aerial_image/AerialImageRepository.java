package com.URBinLAB.aerial_image;

import com.URBinLAB.aerial_image.AerialImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerialImageRepository extends JpaRepository<AerialImage, Long> {
}
