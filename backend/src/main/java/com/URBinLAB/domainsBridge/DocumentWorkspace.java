package com.URBinLAB.domainsBridge;

import com.URBinLAB.dataBridge.DocumentWorkspaceClass;
import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.Workspace;

import javax.persistence.*;

@Entity
@Table(name = "document_workspace", schema = "public")
@IdClass(DocumentWorkspaceClass.class)
public class DocumentWorkspace {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public DocumentWorkspace() {}

    public Document getDocument() {
        return this.document;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }
}
