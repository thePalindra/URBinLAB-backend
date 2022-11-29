package com.URBinLAB.document.statistics;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="statistics", schema = "public")
@Builder
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="statistics_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "document_id")
    private Document document;
    private String theme;

    public Statistics() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getTheme() {
        return this.theme;
    }
}
