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
    @JoinColumn(name = "aerial_image_id", referencedColumnName = "aerial_image_id")
    private AerialImage aerialImage;
    private String resolution;

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
}
