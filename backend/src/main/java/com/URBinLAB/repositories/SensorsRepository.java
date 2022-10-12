package com.URBinLAB.repositories;

import com.URBinLAB.domains.Sensors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, Long> {

    @Query(value = "SELECT DISTINCT s.variable " +
            "FROM \"sensors\" s", nativeQuery = true)
    List<String> getAllVariable();
}
