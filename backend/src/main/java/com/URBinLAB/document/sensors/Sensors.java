package com.URBinLAB.document.sensors;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="sensors", schema = "public")
@Builder
@AllArgsConstructor
public class Sensors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sensors_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;
    private String variable;

    public Sensors() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getVariable() {
        return this.variable;
    }
}
