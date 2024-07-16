package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long sellerId;
    private String storeName;
    private String storeDescription;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Product> products;

}
