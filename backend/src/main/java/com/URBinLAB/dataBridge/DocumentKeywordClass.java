package com.URBinLAB.dataBridge;

import java.io.Serializable;

public class DocumentKeywordClass implements Serializable {

    private Long document;
    private Long keyword;

    public DocumentKeywordClass() {}

    public DocumentKeywordClass(Long document, Long keyword) {
        this.document = document;
        this.keyword = keyword;
    }

    public Long getDocument() {
        return this.document;
    }

    public Long getKeyword() {
        return this.keyword;
    }
}
