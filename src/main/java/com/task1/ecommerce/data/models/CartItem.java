package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long buyersId;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    private Buyer buyer;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    private Cart cart;
    @ManyToOne
    private Product product;
}
