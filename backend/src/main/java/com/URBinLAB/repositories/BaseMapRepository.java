package com.URBinLAB.repositories;

import com.URBinLAB.domains.BaseMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseMapRepository extends JpaRepository<BaseMap, Long> {
}
