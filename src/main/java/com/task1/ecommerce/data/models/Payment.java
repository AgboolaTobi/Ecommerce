package com.task1.ecommerce.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String description;
    private LocalDate paymentDate;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private BuyerOder buyerOder;

}
