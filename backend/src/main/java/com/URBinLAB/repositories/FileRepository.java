package com.URBinLAB.repositories;

import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "Select f from File f Where f.format = :format and f.document = :doc")
    List<File> checkIfExists(@Param("format") String format, @Param("doc") Document doc);
}
