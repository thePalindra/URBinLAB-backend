package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="cartography", schema = "public")
@Builder
@AllArgsConstructor
public class Cartography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartography_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;

    private String scale;
    private Boolean raster;
    @Column(name="image_resolution")
    private String resolution;
    @Column(name="geometry_type")
    private String type;

    public Cartography() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getScale() {
        return this.scale;
    }

    public Boolean getRaster() {
        return this.raster;
    }

    public String getResolution() {
        return this.resolution;
    }

    public String getType() {
        return this.type;
    }
}
