package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="district", schema = "public")
@Builder
@AllArgsConstructor
public class District {

    @Id
    @Column(name="district_id")
    private Long id;
    private String name;

    public District() {}

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
