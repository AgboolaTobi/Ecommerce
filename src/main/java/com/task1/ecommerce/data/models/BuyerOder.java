package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
public class BuyerOder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private String phoneNumber;
    private String amount;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}
