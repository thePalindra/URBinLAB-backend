package com.URBinLAB.document.statistics.surveys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveysRepository extends JpaRepository<Surveys, Long> {
}
