package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Setter
@Getter

public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean isLogin;
    private LocalDate createdAt;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Store> stores;


}
