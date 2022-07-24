package com.URBinLAB.dataBridge;

import java.io.Serializable;

public class CollectionWorkspaceClass implements Serializable {

    private Long collection;
    private Long workspace;

    public CollectionWorkspaceClass() {}

    public CollectionWorkspaceClass (Long collection, Long workspace) {
        this.collection = collection;
        this.workspace = workspace;
    }

    public Long getCollection() {
        return this.collection;
    }

    public Long getWorkspace() {
        return this.workspace;
    }
}
