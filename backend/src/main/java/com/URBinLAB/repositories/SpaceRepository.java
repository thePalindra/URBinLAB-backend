package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name FROM \"space\" s " +
            "WHERE s.level = :level AND s.hierarchy = \'CAOP\' " +
            "ORDER BY s.space_id", nativeQuery = true)
    List<Object> getAllFromLevel(@Param("level") Integer level);
}
