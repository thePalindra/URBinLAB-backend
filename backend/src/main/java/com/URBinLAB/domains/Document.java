package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.geo.Polygon;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "document", schema = "public")
@Builder
@AllArgsConstructor
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "archiver_id", referencedColumnName = "researcher_id")
    private Researcher archiver;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private Space space;

    private String name;
    private String description;
    private String type;
    private String provider;

    @Column(name="time_scope")
    @Temporal(TemporalType.DATE)
    private Date timeScope;

    @Column(name="creation")
    @Temporal(TemporalType.DATE)
    private Date creation;

    private String link;

    public Document() {}

    public void setSpace(Space space) {
        this.space = space;
    }

    public Long getId() {
        return this.id;
    }

    public Collection getCollection() {
        return this.collection;
    }

    public Researcher getArchiver() {
        return this.archiver;
    }

    public Space getSpace() {
        return this.space;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }

    public String getProvider() {
        return this.provider;
    }

    public Date getTimeScope() {
        return this.timeScope;
    }

    public Date getCreation() {
        return this.creation;
    }

    public String getLink() {
        return this.link;
    }
}
