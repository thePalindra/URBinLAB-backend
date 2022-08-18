package com.URBinLAB.repositories;

import com.URBinLAB.domains.Researcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {

    @Query("SELECT r FROM Researcher r WHERE r.name = :name")
    Researcher getByName(@Param("name") String name);

    @Query("SELECT r FROM Researcher r WHERE r.email = :email")
    Researcher getByEmail(@Param("email") String email);

    @Query("SELECT r FROM Researcher r WHERE r.name = :name AND r.password = :password")
    Researcher login(@Param("name") String name,@Param("password") String password);

    @Query(value = "SELECT r.researcher_id, r.name " +
            "FROM \"researcher\" r " +
            "INNER JOIN \"document\" d ON r.researcher_id = d.archiver_id "
            , nativeQuery = true)
    List<Object> getAllArchivers();
}
