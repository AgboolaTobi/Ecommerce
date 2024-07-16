package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
//    @OneToMany(fetch = EAGER,cascade = CascadeType.ALL)
//    private List<Buyer> buyers;
}
