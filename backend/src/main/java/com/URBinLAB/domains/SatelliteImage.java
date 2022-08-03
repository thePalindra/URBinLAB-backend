package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="satellite_image", schema = "public")
@Builder
@AllArgsConstructor
public class SatelliteImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="satellite_image_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "aerial_image_id")
    private AerialImage image;

    private String satellite;
    private String resolution;

    public SatelliteImage() {}

    public Long getId() {
        return this.id;
    }

    public AerialImage getImage() {
        return this.image;
    }

    public String getSatellite() {
        return this.satellite;
    }

    public String getResolution() {
        return this.resolution;
    }
}
