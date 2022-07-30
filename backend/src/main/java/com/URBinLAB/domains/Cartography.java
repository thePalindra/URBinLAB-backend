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

    private Integer scale;
    private String format;

    public Cartography() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public Integer getScale() {
        return this.scale;
    }

    public String getFormat() {
        return this.format;
    }
}
