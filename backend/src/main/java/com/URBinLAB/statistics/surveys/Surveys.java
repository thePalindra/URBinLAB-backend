package com.URBinLAB.statistics.surveys;

import com.URBinLAB.statistics.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="surveys", schema = "public")
@Builder
@AllArgsConstructor
public class Surveys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="surveys_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistics_id", referencedColumnName = "statistics_id")
    private Statistics statistics;

    public Surveys() {}

    public Long getId() {
        return this.id;
    }

    public Statistics getStatistics() {
        return this.statistics;
    }
}
