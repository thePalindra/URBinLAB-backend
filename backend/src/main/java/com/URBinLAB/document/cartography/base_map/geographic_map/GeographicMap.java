package com.URBinLAB.document.cartography.base_map.geographic_map;

import com.URBinLAB.document.cartography.base_map.BaseMap;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="geographic_map", schema = "public")
@Builder
@AllArgsConstructor
public class GeographicMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="geographic_map_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "base_map_id")
    private BaseMap baseMap;

    public GeographicMap() {}

    public Long getId() {
        return this.id;
    }

    public BaseMap getBaseMap() {
        return this.baseMap;
    }
}
