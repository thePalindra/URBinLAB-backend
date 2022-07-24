package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.geo.Polygon;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="collection", schema = "public")
@Builder
@AllArgsConstructor
public class Collection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="collection_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "archiver_id", referencedColumnName = "researcher_id")
    private Researcher archiver;

    private String name;
    private String description;
    private String provider;

    @Column(name="time_scope")
    @Temporal(TemporalType.DATE)
    private Date timeScope;

    public Collection() {}

    public Long getId() {
        return this.id;
    }

    public Researcher getArchiver() {
        return this.archiver;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getProvider() {
        return this.provider;
    }

    public Date getTimeScope() {
        return this.timeScope;
    }
}
