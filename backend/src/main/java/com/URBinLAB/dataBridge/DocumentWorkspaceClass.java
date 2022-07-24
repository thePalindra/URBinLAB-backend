package com.URBinLAB.dataBridge;

import java.io.Serializable;

public class DocumentWorkspaceClass implements Serializable {

    private Long document;
    private Long workspace;

    public DocumentWorkspaceClass() {}

    public DocumentWorkspaceClass(Long document, Long workspace) {
        this.document = document;
        this.workspace = workspace;
    }

    public Long getDocument() {
        return this.document;
    }

    public Long getWorkspace() {
        return this.workspace;
    }
}
