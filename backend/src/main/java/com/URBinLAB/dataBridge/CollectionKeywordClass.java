package com.URBinLAB.dataBridge;

import java.io.Serializable;

public class CollectionKeywordClass implements Serializable {

    private Long collection;
    private Long keyword;

    public CollectionKeywordClass() {}

    public CollectionKeywordClass(Long collection, Long keyword) {
        this.collection = collection;
        this.keyword = keyword;
    }

    public Long getCollection() {
        return this.collection;
    }

    public Long getKeyword() {
        return this.keyword;
    }
}
