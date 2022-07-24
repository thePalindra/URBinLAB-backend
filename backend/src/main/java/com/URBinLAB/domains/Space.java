package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.geo.Polygon;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "space", schema = "public")
@Builder
@AllArgsConstructor
public class Space implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="space_id")
    private Long id;
    private Polygon space;
    private String name;

    public Space() {}

    public Long getId() {
        return this.id;
    }

    public Polygon getSpace() {
        return this.space;
    }

    public String getName() {
        return this.name;
    }
}
