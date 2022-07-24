package com.URBinLAB.repositories;

import com.URBinLAB.domains.Freguesia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreguesiaRepository extends JpaRepository<Freguesia, Long> {
}
