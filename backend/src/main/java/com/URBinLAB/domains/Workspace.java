package com.URBinLAB.domains;

import javax.persistence.*;

@Entity
@Table(name = "workspace", schema = "public")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="workspace_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id", referencedColumnName = "workspace_id")
    private Workspace parent;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "researcher_id")
    private Researcher creator;

    private String name;
    private String description;

    @Column(name = "public")
    private boolean flag;

    public Workspace() {}

    public Long getId() {
        return this.id;
    }

    public Workspace getParent() {
        return this.parent;
    }

    public Researcher getCreator() {
        return this.creator;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isFlag() {
        return this.flag;
    }
}
