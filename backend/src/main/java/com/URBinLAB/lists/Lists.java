package com.URBinLAB.lists;

import com.URBinLAB.collection.Collection;
import com.URBinLAB.researcher.Researcher;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="list", schema = "public")
@Builder
@AllArgsConstructor
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "researcher_id")
    private Researcher researcher;

    @Temporal(TemporalType.DATE)
    private Date created;
    private String name;

    public Lists() {}

    public Long getId() {
        return this.id;
    }

    public Researcher getResearcher() {
        return this.researcher;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
