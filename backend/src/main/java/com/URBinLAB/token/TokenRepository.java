package com.URBinLAB.token;

import com.URBinLAB.researcher.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.researcher = :researcher")
    Token getTokenByUser(@Param("researcher")Researcher researcher);
}
