package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long buyerId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalAmount;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Product> products;

}
