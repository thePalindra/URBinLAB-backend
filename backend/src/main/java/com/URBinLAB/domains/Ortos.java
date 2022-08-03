package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="ortos", schema = "public")
@Builder
@AllArgsConstructor
public class Ortos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ortos_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "aerial_image_id")
    private AerialImage image;
    private String resolution;
    private Integer scale;

    public Ortos() {}

    public Long getId() {
        return this.id;
    }

    public AerialImage getImage() {
        return this.image;
    }

    public String getResolution() {
        return this.resolution;
    }

    public Integer getScale() {
        return this.scale;
    }
}
