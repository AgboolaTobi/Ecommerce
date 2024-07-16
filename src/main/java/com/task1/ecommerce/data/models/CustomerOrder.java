package com.task1.ecommerce.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String deliveryAddress;
    private String phoneNumber;
    private String amount;
    @ManyToOne
    private Customer customer;
}
