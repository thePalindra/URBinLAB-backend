package com.URBinLAB.statistics.census;

import com.URBinLAB.statistics.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="census", schema = "public")
@Builder
@AllArgsConstructor
public class Census {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="census_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistics_id", referencedColumnName = "statistics_id")
    private Statistics statistics;

    public Census() {}

    public Long getId() {
        return this.id;
    }

    public Statistics getStatistics() {
        return this.statistics;
    }
}
