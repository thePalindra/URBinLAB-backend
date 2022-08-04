package com.URBinLAB.repositories;

import com.URBinLAB.domains.Census;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CensusRepository extends JpaRepository<Census, Long> {
}
