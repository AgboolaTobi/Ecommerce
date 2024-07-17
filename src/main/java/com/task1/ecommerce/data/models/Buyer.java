package com.task1.ecommerce.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter

public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Cart cart;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private List<BuyerOder> buyerOrders;
}
