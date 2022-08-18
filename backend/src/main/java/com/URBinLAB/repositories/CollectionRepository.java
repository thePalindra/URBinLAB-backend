package com.URBinLAB.repositories;

import com.URBinLAB.domains.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query(value = "SELECT col.collection_id, col.name " +
            "FROM \"collection\" col " +
            "NATURAL JOIN \"document\" d "
            , nativeQuery = true)
    List<Object> getAllCollections();
}
