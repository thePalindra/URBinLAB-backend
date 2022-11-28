package com.URBinLAB.collection;

import com.URBinLAB.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query(value = "SELECT col.collection_id, col.name " +
            "FROM \"collection\" col " +
            "NATURAL JOIN \"document\" d "
            , nativeQuery = true)
    List<Object> getAllCollections();
}
