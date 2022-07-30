package com.URBinLAB.repositories;

import com.URBinLAB.domains.Photography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographyRepository extends JpaRepository<Photography, Long> {
}
