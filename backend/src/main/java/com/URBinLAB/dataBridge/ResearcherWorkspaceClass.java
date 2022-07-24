package com.URBinLAB.dataBridge;

import java.io.Serializable;

public class ResearcherWorkspaceClass implements Serializable {

    private Long researcher;
    private Long workspace;

    public ResearcherWorkspaceClass() {}

    public ResearcherWorkspaceClass (Long collection, Long workspace) {
        this.researcher = collection;
        this.workspace = workspace;
    }

    public Long getResearcher() {
        return this.researcher;
    }

    public Long getWorkspace() {
        return this.workspace;
    }
}
