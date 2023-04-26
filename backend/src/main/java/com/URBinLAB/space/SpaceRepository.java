package com.URBinLAB.space;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name \n" +
            "FROM \"space\" s \n" +
            "WHERE s.level = :level AND s.hierarchy = \'CAOP\' \n" +
            "ORDER BY s.space_id", nativeQuery = true)
    List<Object> getAllFromLevel(@Param("level") Integer level);

    @Query(value = "SELECT s.space_id, ST_AsText(s.space), s.name, ST_AsText(ST_Centroid(s.space)) as center, ST_Area(s.space) * 0.3048 ^ 2 " +
            "FROM \"space\" s " +
            "WHERE s.hierarchy LIKE %:hierarchy% " +
            "AND s.level_name LIKE %:level% " +
            "AND s.name = :name " , nativeQuery = true)
    List<Object> searchByName(@Param("name") String name, @Param("level") String level, @Param("hierarchy") String hierarchy);

    @Query(value = "SELECT res.doc_id\n" +
            "FROM (SELECT d.document_id doc_id, s.space sp1, s.space_id spid\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"space\" s\n" +
            "ON s.space_id = d.space_id) res\n" +
            "FULL OUTER JOIN \"space\" s\n" +
            "ON ST_Intersects(Geometry(s.space), Geometry(res.sp1))\n" +
            "WHERE s.space_id = :id\n", nativeQuery = true)
    List<Object> getAllTheDocuments(@Param("id") Long id);

    @Query(value = "SELECT res.doc_id, res.col_id, res.doc_type, res.doc_arc, res.doc_name, res.doc_year\n" +
            "FROM (SELECT d.document_id doc_id, d.collection_id col_id, d.type doc_type, d.archiver_id doc_arc, d.name doc_name, EXTRACT(YEAR FROM d.time_scope) doc_year, s.space sp1, s.space_id spid\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"space\" s\n" +
            "ON s.space_id = d.space_id) res\n" +
            "FULL OUTER JOIN \"space\" s\n" +
            "ON ST_Intersects(Geometry(s.space), Geometry(res.sp1))\n" +
            "WHERE s.space_id = :id\n" +
            "AND res.doc_id IN :list \n", nativeQuery = true)
    List<Object> getAllTheDocuments(@Param("id") Long id, @Param("list") List<Integer> list);

    @Query(value = "SELECT DISTINCT d.document_id\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(ST_GeomFromText(:space, 4326), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByGeometry(@Param("space") String space);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(ST_GeomFromText(:space, 4326), Geometry(s.space)) \n" +
            "AND d.document_id IN :list \n", nativeQuery = true)
    List<Object> getAllTheDocumentsByGeometry(@Param("space") String space, @Param("list") List<Integer> list);

    @Query(value = "SELECT DISTINCT d.document_id, ST_Area(s.space) area\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(s.space), ST_GeomFromText(:space, 4326))\n" +
            "ORDER BY area", nativeQuery = true)
    List<Object> getAllTheDocumentsByMarker(@Param("space") String space);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope), ST_Area(s.space) area\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(s.space), ST_GeomFromText(:space, 4326))\n" +
            "AND d.document_id IN :list \n" +
            "ORDER BY area", nativeQuery = true)
    List<Object> getAllTheDocumentsByMarker(@Param("space") String space, @Param("list") List<Integer> list);

    @Query(value = "SELECT DISTINCT d.document_id\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(ST_Buffer(Geography(ST_MakePoint(?1, ?2)), ?3)), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsByCircle(Double lng, Double lat, Double size);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(ST_Buffer(Geography(ST_MakePoint(?1, ?2)), ?3)), Geometry(s.space))\n" +
            "AND d.document_id IN ?4 \n", nativeQuery = true)
    List<Object> getAllTheDocumentsByCircle(Double lng, Double lat, Double size, List<Integer> list);

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

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(ST_GeomFromText(:space, 4326), Geometry(s.space))\n" +
            "AND d.document_id IN :list", nativeQuery = true)
    List<Object> getDocumentListGeometry(@Param("space") String space,
                                         @Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(ST_Buffer(Geography(ST_MakePoint(:lng, :lat)), :size)), Geometry(s.space))\n" +
            "AND d.document_id IN :list", nativeQuery = true)
    List<Object> getDocumentListCircle(@Param("lng") Double lng,
                                       @Param("lat") Double lat,
                                       @Param("size") Double size,
                                       @Param("list") List<Integer> list);

    @Query(value = "SELECT d.document_id, d.collection_id, d.type, d.archiver_id, d.name, EXTRACT(YEAR FROM d.time_scope)\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE ST_Intersects(Geometry(s.space), ST_GeomFromText(:space, 4326))\n" +
            "AND d.document_id IN :list", nativeQuery = true)
    List<Object> getDocumentListMarker(@Param("space") String space,
                                       @Param("list") List<Integer> list);

    @Query(value = "SELECT DISTINCT d.document_id\n" +
            "FROM \"space\" s\n" +
            "INNER JOIN \"document\" d \n" +
            "ON s.space_id = d.space_id\n" +
            "WHERE s.space_id = :id\n" +
            "AND (ST_Intersects(ST_GeomFromText(:space, 4326), Geometry(s.space))", nativeQuery = true)
    List<Object> getAllTheDocumentsBySpaceId(@Param("id") Long id);

    @Query(value="SELECT CONCAT(hierarchy, ', ', level_name, ', ', name) AS string_output\n" +
            "FROM \"space\"\n" +
            "WHERE hierarchy_type IS NOT NULL\n" +
            "ORDER BY space_id", nativeQuery = true)
    List<Object> getAllSpaces();

    @Query(value="SELECT space_id \n" +
            "FROM \"space\" \n" +
            "WHERE hierarchy_type IS NOT null \n" +
            "ORDER BY space_id", nativeQuery = true)
    List<Object> getAllSpaceIds();
}
