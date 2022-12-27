package com.URBinLAB.keywords;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="keyword", schema = "public")
@Builder
@AllArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;
    private String keyword;

    public Keyword() {}

    public Long getId() {
        return this.id;
    }

    public String getKeyword() {
        return this.keyword;
    }
}
