package com.URBinLAB.file;

import com.URBinLAB.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file", schema = "public")
@Builder
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "document_id")
    private Document document;

    private String file;

    private String name;

    @Column(name="format")
    private String format;

    @Column(name="creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private Long size;

    public File() {}

    public void setDocument(Document document) {
        this.document = document;
    }

    public Long getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    public String getFile() {
        return this.file;
    }

    public String getFormat() {
        return this.format;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Long getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }
}
