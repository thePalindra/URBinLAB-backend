package com.URBinLAB.repositories;

import com.URBinLAB.domains.Concelho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcelhoRepository extends JpaRepository<Concelho, Long> {
}
