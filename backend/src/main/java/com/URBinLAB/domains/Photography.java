package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="photography", schema = "public")
@Builder
@AllArgsConstructor
public class Photography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photography_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;
    @Column(name="image_resolution")
    private String resolution;
    private Boolean color;

    public Photography() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getResolution() {
        return this.resolution;
    }

    public Boolean isColored() {
        return this.color;
    }
}
