package com.URBinLAB.researcher;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name="researcher", schema = "public")
@Builder
@AllArgsConstructor
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="researcher_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    public Researcher() {}

    public void sha256Pass() {
        this.password = DigestUtils.sha256Hex(this.password);
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }
}
