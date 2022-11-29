package com.URBinLAB.document.statistics.thematic_statistics;

import com.URBinLAB.document.statistics.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="thematic_statistics", schema = "public")
@Builder
@AllArgsConstructor
public class ThematicStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thematic_statistics_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistics_id", referencedColumnName = "statistics_id")
    private Statistics statistics;

    public ThematicStatistics() {}

    public Long getId() {
        return this.id;
    }

    public Statistics getStatistics() {
        return this.statistics;
    }
}
