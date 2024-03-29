package com.URBinLAB.researcher;

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

    @Query("SELECT r FROM Researcher r WHERE r.name = :name AND r.password = :password AND r.active = true AND r.deleted = false")
    Researcher login(@Param("name") String name,@Param("password") String password);

    @Query(value = "SELECT DISTINCT r.researcher_id, r.name " +
            "FROM \"researcher\" r " +
            "INNER JOIN \"document\" d ON r.researcher_id = d.archiver_id "
            , nativeQuery = true)
    List<Object> getAllArchivers();

    @Query(value = "SELECT \"name\"\n" +
            "FROM \"researcher\"\n" +
            "WHERE researcher_id = :id", nativeQuery = true)
    Object getArchiverName(@Param("id") Long id);

    @Query(value = "SELECT researcher_id, \"name\", email\n" +
            "FROM \"researcher\"\n" +
            "WHERE active = false\n" +
            "AND deleted = false", nativeQuery = true)
    List<Object> getAllInactive();

    @Query(value = "SELECT researcher_id, \"name\", email\n" +
            "FROM \"researcher\"\n" +
            "WHERE deleted = true", nativeQuery = true)
    List<Object> getAllDeleted();

    @Query(value = "SELECT researcher_id, \"name\", email, role\n" +
            "FROM \"researcher\"\n" +
            "WHERE active = true\n" +
            "AND deleted = false\n" +
            "AND role != \'admin\'", nativeQuery = true)
    List<Object> getAllActive();
}
