package com.URBinLAB.document.reports;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="reports", schema = "public")
@Builder
@AllArgsConstructor
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reports_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;
    private String context;
    private String theme;

    public Reports() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getContext() {
        return this.context;
    }

    public String getTheme() {
        return this.theme;
    }
}
