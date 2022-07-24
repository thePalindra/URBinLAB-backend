package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name="freguesia", schema = "public")
@Builder
@AllArgsConstructor
public class Freguesia {

    @Id
    @Column(name="freguesia_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "space_id", referencedColumnName = "space_id")
    private Space space;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concelho_id", referencedColumnName = "district_id")
    private Concelho concelho;

    private String name;

    public Freguesia() {}

    public Long getId() {
        return this.id;
    }

    public Space getSpace() {
        return this.space;
    }

    public String getName() {
        return this.name;
    }

    public Concelho getConcelho() {
        return this.concelho;
    }
}
