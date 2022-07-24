package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Space_Repository extends JpaRepository<Space, Long> {
}
