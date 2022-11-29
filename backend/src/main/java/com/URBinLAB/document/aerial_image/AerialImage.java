package com.URBinLAB.document.aerial_image;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="aerial_image", schema = "public")
@Builder
@AllArgsConstructor
public class AerialImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="aerial_image_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "document_id")
    private Document document;

    public AerialImage() {}

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

}
