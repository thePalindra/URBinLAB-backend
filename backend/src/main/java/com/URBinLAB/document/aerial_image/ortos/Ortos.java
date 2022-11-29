package com.URBinLAB.document.aerial_image.ortos;

import com.URBinLAB.document.aerial_image.AerialImage;
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
    private String scale;

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

    public String getScale() {
        return this.scale;
    }
}
