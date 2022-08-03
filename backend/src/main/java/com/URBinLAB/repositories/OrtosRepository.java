package com.URBinLAB.repositories;

import com.URBinLAB.domains.Ortos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrtosRepository extends JpaRepository<Ortos, Long> {
}
