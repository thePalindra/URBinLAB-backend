package com.URBinLAB.document.cartography.thematic_map;

import com.URBinLAB.document.cartography.Cartography;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="thematic_map", schema = "public")
@Builder
@AllArgsConstructor
public class ThematicMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thematic_map_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartography_id", referencedColumnName = "cartography_id")
    private Cartography cartography;
    private String theme;
    @Column(name = "thematic_map_type")
    private String type;

    public ThematicMap() {}

    public Long getId() {
        return this.id;
    }

    public Cartography getCartography() {
        return this.cartography;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getType() {
        return this.type;
    }
}
