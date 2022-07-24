package com.URBinLAB.domainsBridge;

import com.URBinLAB.dataBridge.CollectionKeywordClass;
import com.URBinLAB.domains.Collection;
import com.URBinLAB.domains.Keyword;

import javax.persistence.*;

@Entity
@Table(name="collection_keyword", schema = "public")
@IdClass(CollectionKeywordClass.class)
public class CollectionKeyword {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public CollectionKeyword() {}

    public Collection getCollection() {
        return this.collection;
    }

    public Keyword getKeyword() {
        return this.keyword;
    }
}
