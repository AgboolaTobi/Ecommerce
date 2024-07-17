package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private Long buyerId;
    private String deliveryAddress;
    private String phoneNumber;
    private BigDecimal amount;
    private String preferredDeliveryDate;
    private LocalDateTime orderDate;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    private List<CartItem> cartItems;
}
