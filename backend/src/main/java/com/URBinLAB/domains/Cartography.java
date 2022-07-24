package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="aerial_image", schema = "public")
@Builder
@AllArgsConstructor
public class Cartography implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartography_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;
    private Integer scale;

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
}
