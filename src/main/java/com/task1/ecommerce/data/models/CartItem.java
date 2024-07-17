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
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    private Cart cart;
    @ManyToOne
    private Product product;
}
