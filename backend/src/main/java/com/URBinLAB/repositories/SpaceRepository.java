package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.geolatte.geom.Geometry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Modifying
    @Query(value = "insert into \"space\" (space_id, \"name\", \"space\") VALUES (:id, :name, ST_GeomFromText(:space))", nativeQuery = true)
    @Transactional
    void insert(@Param("id") Long id, @Param("name") String name, @Param("space") String space);

    @Query(value = "SELECT max(space_id) FROM \"space\"", nativeQuery = true)
    Long getMax();

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name FROM \"space\" s " +
            "WHERE s.level = :level AND s.hierarchy = \'CAOP\' " +
            "ORDER BY s.space_id", nativeQuery = true)
    List<Object> getAllFromLevel(@Param("level") Integer level);

    @Query(value = "SELECT d.space_id, ST_AsText(d.space), d.name " +
            "FROM \"space\" d " +
            "WHERE d.name LIKE :name% " +
            "AND d.level = :level", nativeQuery = true)
    List<Object> searchByName(@Param("level") Integer level, @Param("name") String name);


    @Query(value = "SELECT mu.space_id, ST_AsText(mu.space), mu.name " +
            "FROM \"space\" d, \"space\" mu\n" +
            "WHERE d.name LIKE :name% " +
            "AND d.level = :level " +
            "AND d.hierarchy = :hierarchy " +
            "AND d.space_id = mu.parent", nativeQuery = true)
    List<Object> getTheLevelBellow(@Param("name") String name, @Param("hierarchy") String hierarchy, @Param("level") Integer lvl);

    @Query(value = "select fr.space_id, ST_AsText(fr.space), fr.name \n" +
            "from \"space\" d, \"space\" mu, \"space\" fr\n" +
            "where d.name LIKE :name% \n" +
            "and d.level = 1 " +
            "and d.hierarchy = :hierarchy\n" +
            "and d.space_id = mu.parent\n" +
            "and mu.space_id = fr.parent", nativeQuery = true)
    List<Object> getEverything(@Param("name") String name, @Param("hierarchy") String hierarchy);

    @Query(value = "SELECT s2.name, s2.space_id, d.name, d.document_id, d.type, d.time_scope\n" +
            "FROM \"space\" s1\n" +
            "INNER JOIN \"space\" s2 ON s2.parent = s1.space_id\n" +
            "INNER JOIN \"document\" d ON s2.space_id = d.space_id\n" +
            "WHERE ST_Contains(s1.space, s2.space)\n" +
            "AND s1.space_id = :id", nativeQuery = true)
    List<Object> getAllTheDocuments(Pageable pageable, @Param("id") Long id);


    @Query(value = "SELECT d.space_id, d.name, d.document_id, d.type, d.time_scope\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Contains(ST_GeomFromText(:space), s.space)", nativeQuery = true)
    List<Object> getAllTheDocumentsByGeometry(Pageable pageable, @Param("space") String space);

}
