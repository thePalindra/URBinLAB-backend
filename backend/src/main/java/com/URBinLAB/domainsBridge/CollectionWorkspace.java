package com.URBinLAB.domainsBridge;

import com.URBinLAB.dataBridge.CollectionWorkspaceClass;
import com.URBinLAB.domains.Collection;
import com.URBinLAB.domains.Workspace;

import javax.persistence.*;

@Entity
@Table(name = "collection_workspace", schema = "public")
@IdClass(CollectionWorkspaceClass.class)
public class CollectionWorkspace {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public CollectionWorkspace() {}

    public Collection getCollection() {
        return this.collection;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }
}
