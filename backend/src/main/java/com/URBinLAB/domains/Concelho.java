package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="concelho", schema = "public")
@Builder
@AllArgsConstructor
public class Concelho {

    @Id
    @Column(name="concelho_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    private District district;

    private String name;

    public Concelho() {}

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public District getDistrict() {
        return this.district;
    }
}
