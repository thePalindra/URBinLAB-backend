package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="base_map", schema = "public")
@Builder
@AllArgsConstructor
public class BaseMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="base_map_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "cartography_id")
    private Cartography cartography;

    public BaseMap() {}

    public Long getId() {
        return this.id;
    }

    public Cartography getCartography() {
        return this.cartography;
    }
}
