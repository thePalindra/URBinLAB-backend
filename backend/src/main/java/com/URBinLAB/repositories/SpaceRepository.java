package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.websocket.server.PathParam;
import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Modifying
    @Query(value = "insert into \"space\" (space_id, \"name\", \"space\") VALUES (:id, :name, Geography(ST_GeomFromText(:space)))", nativeQuery = true)
    @Transactional
    void insertWKT(@Param("id") Long id, @Param("name") String name, @Param("space") String space);

    @Modifying
    @Query(value = "insert into \"space\" (space_id, \"name\", \"space\") VALUES (:id, :name, Geography(ST_GeomFromGeoJSON(:space)))", nativeQuery = true)
    @Transactional
    void insertGeoJson(@Param("id") Long id, @Param("name") String name, @Param("space") String space);

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
            "FROM \"space\" s " +
            "WHERE s.hierarchy = :hierarchy " +
            "AND s.level_name = :level " +
            "AND s.name LIKE %:name% " , nativeQuery = true)
    List<Object> searchByName(@Param("name") String name, @Param("level") String level, @Param("hierarchy") String hierarchy);

    @Query(value = "SELECT s2.name, s2.space_id, d.name, d.document_id, d.type, d.time_scope\n" +
            "FROM \"space\" s1\n" +
            "INNER JOIN \"space\" s2 ON s2.space_id = s1.space_id\n" +
            "INNER JOIN \"document\" d ON s2.space_id = d.space_id\n" +
            "WHERE s1.space_id = :id \n" +
            "AND ST_Contains(Geometry(s1.space), Geometry(s2.space))", nativeQuery = true)
    List<Object> getAllTheDocuments(Pageable pageable, @Param("id") Long id);


    @Query(value = "SELECT DISTINCT d.document_id, d.collection_id, d.archiver_id, d.space_id, d.name, d.description, d.type, d.provider, d.time_scope, d.insertion, d.link\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Contains(ST_GeomFromText(:space, 4326), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByGeometry(@Param("space") String space);


    @Query(value = "SELECT DISTINCT d.document_id, d.collection_id, d.archiver_id, d.space_id, d.name, d.description, d.type, d.provider, d.time_scope, d.insertion, d.link\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Contains(Geometry(ST_Buffer(Geography(ST_MakePoint(?1, ?2)), ?3)), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByCircle(Double lng, Double lat, Double size);

    @Query(value = "SELECT DISTINCT s.hierarchy_type " +
            "FROM \"space\" s " +
            "WHERE s.hierarchy_type is not null " +
            "ORDER BY s.hierarchy_type", nativeQuery = true)
    List<Object> getAllHierarchyTypes();

    @Query(value = "SELECT DISTINCT s.hierarchy " +
            "FROM \"space\" s " +
            "WHERE s.hierarchy is not null " +
            "AND s.hierarchy_type = :type " +
            "ORDER BY s.hierarchy", nativeQuery = true)
    List<Object> getAllHierarchies(@Param("type") String type);

    @Query(value = "SELECT DISTINCT s.level_name " +
            "FROM \"space\" s " +
            "WHERE s.hierarchy = :name " +
            "ORDER BY s.level_name", nativeQuery = true)
    List<Object> getAllLevels(@Param("name") String name);

    @Query(value = "SELECT DISTINCT s.name " +
            "FROM \"space\" s " +
            "WHERE s.hierarchy = :hierarchy " +
            "AND s.level_name = :level " +
            "ORDER BY s.name ",  nativeQuery = true)
    List<Object> getAllNamesFromLevel(@Param("hierarchy") String hierarchy, @Param("level") String level);
}
