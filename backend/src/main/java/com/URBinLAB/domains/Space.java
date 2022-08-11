package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.geolatte.geom.Geometry;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "space", schema = "public")
@Builder
@AllArgsConstructor
public class Space implements Serializable {

    @Id
    @Column(name="space_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent", referencedColumnName = "space_id")
    private Space parent;

    private String hierarchy;

    private transient Geometry space;
    private String name;
    private Integer level;
    @Column(name="level_name")
    private String levelName;

    public Space() {}

    public Long getId() {
        return this.id;
    }

    public Space getParent() {
        return this.parent;
    }

    public Integer getLevel() {
        return this.level;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public String getName() {
        return this.name;
    }
}
