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
    @Query(value = "insert into \"space\" (space_id, \"name\", \"space\") VALUES (:id, :name, Geography(ST_GeomFromText(:space)))", nativeQuery = true)
    @Transactional
    void insert(@Param("id") Long id, @Param("name") String name, @Param("space") String space);

    @Modifying
    @Query(value = "insert into \"space\" (space_id, \"name\", \"space\") " +
            "VALUES (?1, ?2, ST_Buffer(Geography(ST_MakePoint(?3, ?4)), ?5))", nativeQuery = true)
    @Transactional
    void insertCircle(Long id, String name, Double lng, Double lat, Double size);

    @Query(value = "SELECT max(space_id) FROM \"space\"", nativeQuery = true)
    Long getMax();

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name FROM \"space\" s " +
            "WHERE s.level = :level AND s.hierarchy = \'CAOP\' " +
            "ORDER BY s.space_id", nativeQuery = true)
    List<Object> getAllFromLevel(@Param("level") Integer level);

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name " +
            "FROM \"space\" d, \"space\" s " +
            "WHERE d.name LIKE :name% " +
            "AND d.level = :thisLevel " +
            "AND s.level = :level " +
            "AND ST_Contains(Geometry(d.space), Geometry(s.space))", nativeQuery = true)
    List<Object> searchByName(@Param("level") Integer level, @Param("name") String name, @Param("thisLevel") Integer thisLevel);

    @Query(value = "SELECT s2.name, s2.space_id, d.name, d.document_id, d.type, d.time_scope\n" +
            "FROM \"space\" s1\n" +
            "INNER JOIN \"space\" s2 ON s2.space_id = s1.space_id\n" +
            "INNER JOIN \"document\" d ON s2.space_id = d.space_id\n" +
            "WHERE s1.space_id = :id \n" +
            "AND ST_Contains(Geometry(s1.space), Geometry(s2.space))", nativeQuery = true)
    List<Object> getAllTheDocuments(Pageable pageable, @Param("id") Long id);


    @Query(value = "SELECT DISTINCT d.document_id, d.space_id, d.name, d.type, d.time_scope\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Contains(ST_GeomFromText(:space, 4326), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByGeometry(Pageable pageable, @Param("space") String space);


    @Query(value = "SELECT DISTINCT d.document_id, d.space_id, d.name, d.type, d.time_scope\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Contains(Geometry(ST_Buffer(Geography(ST_MakePoint(?1, ?2)), ?3)), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByCircle(Pageable pageable, Double lng, Double lat, Double size);
}
