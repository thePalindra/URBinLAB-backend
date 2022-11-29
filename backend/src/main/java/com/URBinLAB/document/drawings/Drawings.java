package com.URBinLAB.document.drawings;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="drawings", schema = "public")
@Builder
@AllArgsConstructor
public class Drawings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="drawings_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;
    private String context;

    public Drawings() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getContext() {
        return this.context;
    }
}
