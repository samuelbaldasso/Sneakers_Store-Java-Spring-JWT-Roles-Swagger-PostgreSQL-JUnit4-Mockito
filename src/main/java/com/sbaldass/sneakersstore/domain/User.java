package com.sbaldass.sneakersstore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    private String email;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    private String photoUrl;

    @OneToMany
    Set<Order> orders = new HashSet<>();

    private String address;

    public User(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.password = hashedPassword;
    }

    public User(){

    }
}
