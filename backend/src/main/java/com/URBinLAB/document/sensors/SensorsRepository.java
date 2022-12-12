package com.URBinLAB.document.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, Long> {

    @Query(value = "SELECT DISTINCT s.variable " +
            "FROM \"sensors\" s " +
            "ORDER BY s.variable", nativeQuery = true)
    List<String> getAllVariable();

    @Query(value = "SELECT ss.variable\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"sensors\" ss\n" +
            "ON ss.document_id = d.document_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
