package com.URBinLAB.domainsBridge;

import com.URBinLAB.dataBridge.ResearcherWorkspaceClass;
import com.URBinLAB.domains.Researcher;
import com.URBinLAB.domains.Workspace;

import javax.persistence.*;

@Entity
@Table(name = "researcher_workspace", schema = "public")
@IdClass(ResearcherWorkspaceClass.class)
public class ResearcherWorkspace {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "researcher_id")
    private Researcher researcher;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public ResearcherWorkspace() {}

    public Researcher getResearcher() {
        return this.researcher;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }
}
