package com.URBinLAB.repositories;

import com.URBinLAB.domains.Space;
import com.URBinLAB.queryResults.AllDocumentListing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    @Query(value = "SELECT FROM \"space\"" +
            "WHERE \"leve\" = :level"
            , nativeQuery = true)
    List<Space> getAllFromLevel(@Param("level") Integer level);
}
