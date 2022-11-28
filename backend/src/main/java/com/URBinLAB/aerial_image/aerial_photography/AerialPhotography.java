package com.URBinLAB.aerial_image.aerial_photography;

import com.URBinLAB.aerial_image.AerialImage;
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
    @JoinColumn(name = "aerial_image_id", referencedColumnName = "aerial_image_id")
    private AerialImage aerialImage;
    @Column(name="image_resolution")
    private String resolution;
    @Column(name="approximate_scale")
    private String scale;

    public AerialPhotography() {}

    public Long getId() {
        return this.id;
    }

    public AerialImage getCartography() {
        return this.aerialImage;
    }

    public String getResolution() {
        return this.resolution;
    }

    public AerialImage getAerialImage() {
        return this.aerialImage;
    }

    public String getScale() {
        return this.scale;
    }
}
