package com.URBinLAB.repositories;

import com.URBinLAB.domains.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("select k from Keyword k where k.keyword like %:keyword% ")
    List<Keyword> getKeywordByName (@Param("keyword") String keyword);
}
