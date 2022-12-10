package com.URBinLAB.file;

import com.URBinLAB.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "Select f from File f Where f.format = :format and f.document = :doc")
    List<File> checkIfExists(@Param("format") String format, @Param("doc") Document doc);

    @Query(value = "SELECT f.file_id, f.name, f.format, f.size\n" +
            "FROM \"file\" f\n" +
            "WHERE f.document_id = :id", nativeQuery = true)
    List<Object> getFiles(@Param("id") Long id);
}
