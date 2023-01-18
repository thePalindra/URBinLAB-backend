package com.URBinLAB.lists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ListsRepository extends JpaRepository<Lists, Long> {

    @Query(value = "SELECT l.list_id, l.name\n" +
            "FROM \"list\" l\n", nativeQuery = true)
    List<Object> getAllLists();

    @Query(value = "SELECT l.list_id\n" +
            "FROM \"list\" l\n" +
            "WHERE l.researcher_id=:id\n" +
            "AND l.name=:name", nativeQuery = true)
    Long get(@Param("id") Long id, @Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT \n" +
            "INTO \"document_list\" (document_id, list_id, added)\n" +
            "VALUES (:document, :list, :date)", nativeQuery = true)
    @Transactional
    void addDocument(@Param("list") Long listId, @Param("document") Long id, @Param("date") Date date);

    @Query(value = "SELECT l.list_id, l.name, COUNT(dl.document_id), l.created\n" +
            "FROM \"list\" l\n" +
            "LEFT JOIN \"document_list\" dl\n" +
            "ON dl.list_id=l.list_id\n" +
            "WHERE l.name!='Histórico'\n" +
            "AND l.name!='Favoritos'\n" +
            "AND l.researcher_id=:id\n" +
            "GROUP BY l.list_id\n" +
            "ORDER BY l.name", nativeQuery = true)
    List<Object> getAll(@Param("id") Long id);

    @Query(value = "SELECT l.list_id, l.name, COUNT(dl.document_id), l.created\n" +
            "FROM \"list\" l\n" +
            "LEFT JOIN \"document_list\" dl\n" +
            "ON dl.list_id=l.list_id\n" +
            "WHERE l.researcher_id=:id\n" +
            "AND l.name=:name\n" +
            "GROUP BY l.list_id", nativeQuery = true)
    List<Object> getByName(@Param("id") Long id, @Param("name") String name);
}
