package com.URBinLAB.document.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query(value = "SELECT DISTINCT s.theme " +
            "FROM \"statistics\" s " +
            "ORDER BY s.theme", nativeQuery = true)
    List<String> getAllThemes();

    @Query(value = "SELECT st.theme\n" +
            "FROM \"document\" d\n" +
            "INNER JOIN \"statistics\" st\n" +
            "ON st.document_id = d.document_id\n" +
            "WHERE d.document_id = :id", nativeQuery = true)
    Object getByDocId(@Param("id") Long id);
}
