package com.URBinLAB.repositories;

import com.URBinLAB.domains.Drawings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawingsRepository extends JpaRepository<Drawings, Long> {
}
