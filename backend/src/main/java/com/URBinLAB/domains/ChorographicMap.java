package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="chorographic_map", schema = "public")
@Builder
@AllArgsConstructor
public class ChorographicMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chorographic_map_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "base_map_id")
    private BaseMap baseMap;

    public ChorographicMap() {}

    public Long getId() {
        return this.id;
    }

    public BaseMap getBaseMap() {
        return this.baseMap;
    }
}
