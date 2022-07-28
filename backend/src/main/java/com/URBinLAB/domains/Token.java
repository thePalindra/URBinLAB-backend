package com.URBinLAB.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="token", schema = "public")
@Builder
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "researcher_id", referencedColumnName = "researcher_id")
    private Researcher researcher;
    private String token;
    private String role;

    @Temporal(TemporalType.DATE)
    private Date login;

    public Token() {
    }

    public Long getId() {
        return this.id;
    }

    public Researcher getResearcher() {
        return this.researcher;
    }

    public String getToken() {
        return this.token;
    }

    public String getRole() {
        return this.role;
    }

    public Date getLogin() {
        return this.login;
    }

}
