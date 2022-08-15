package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.geolatte.geom.Geometry;
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

    @Query(value = "SELECT d.space_id, ST_AsText(d.space), d.name " +
            "FROM \"space\" d " +
            "WHERE d.name LIKE :name% AND d.level = :level", nativeQuery = true)
    List<Object> searchByName(@Param("level") Integer level, @Param("name") String name);


    @Query(value = "SELECT mu.space_id, ST_AsText(mu.space), mu.name " +
            "FROM \"space\" d, \"space\" mu\n" +
            "WHERE d.name LIKE :name AND d.hierarchy = :hierarchy AND d.space_id = mu.parent", nativeQuery = true)
    List<Object> getTheLevelBellow(@Param("name") String name, @Param("hierarchy") String hierarchy);

    @Query(value = "select fr.space_id, ST_AsText(fr.space), fr.name \n" +
            "from \"space\" d, \"space\" mu, \"space\" fr\n" +
            "where d.name LIKE :name \n" +
            "and d.level = 1 " +
            "and d.hierarchy = :hierarchy\n" +
            "and d.space_id = mu.parent\n" +
            "and mu.space_id = fr.parent", nativeQuery = true)
    List<Object> getEverything(@Param("name") String name, @Param("hierarchy") String hierarchy);

}
