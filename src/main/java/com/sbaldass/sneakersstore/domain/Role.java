package com.sbaldass.sneakersstore.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoleName name;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    private String description;

    @Override
    public String getAuthority() {
        return name.name();
    }
}