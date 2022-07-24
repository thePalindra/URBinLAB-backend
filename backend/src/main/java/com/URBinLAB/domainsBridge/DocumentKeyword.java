package com.URBinLAB.domainsBridge;

import com.URBinLAB.dataBridge.DocumentKeywordClass;
import com.URBinLAB.domains.Document;
import com.URBinLAB.domains.Keyword;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "document_keyword", schema = "public")
@IdClass(DocumentKeywordClass.class)
@Builder
@AllArgsConstructor
public class DocumentKeyword {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public DocumentKeyword() {}

    public Document getDocument() {
        return this.document;
    }

    public Keyword getKeyword() {
        return this.keyword;
    }
}
