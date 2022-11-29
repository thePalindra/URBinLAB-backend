package com.URBinLAB.document.aerial_image.lidar;

import com.URBinLAB.document.aerial_image.AerialImage;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="lidar", schema = "public")
@Builder
@AllArgsConstructor
public class LiDAR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lidar_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "aerial_image_id")
    private AerialImage image;
    private String resolution;

    public LiDAR() {}

    public Long getId() {
        return this.id;
    }

    public AerialImage getImage() {
        return this.image;
    }

    public String getResolution() {
        return this.resolution;
    }
}
