package com.URBinLAB.document.cartography.base_map.topographic_plan;

import com.URBinLAB.document.cartography.base_map.BaseMap;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="topographic_plan", schema = "public")
@Builder
@AllArgsConstructor
public class TopographicPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="topographic_plan_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "base_map_id")
    private BaseMap baseMap;

    public TopographicPlan() {}

    public Long getId() {
        return this.id;
    }

    public BaseMap getBaseMap() {
        return this.baseMap;
    }
}
