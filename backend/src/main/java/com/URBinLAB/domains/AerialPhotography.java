package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="aerial_photography", schema = "public")
@Builder
@AllArgsConstructor
public class AerialPhotography implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="aerial_photography_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartography_id", referencedColumnName = "cartography_id")
    private Cartography cartography;
    private String resolution;

    public AerialPhotography() {}

    public Long getId() {
        return this.id;
    }

    public Cartography getCartography() {
        return this.cartography;
    }

    public String getResolution() {
        return this.resolution;
    }
}
