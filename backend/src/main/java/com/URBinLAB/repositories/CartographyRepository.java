package com.URBinLAB.repositories;

import com.URBinLAB.domains.Cartography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartographyRepository extends JpaRepository<Cartography, Long> {
}
