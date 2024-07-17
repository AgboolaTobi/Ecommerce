package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderRequest {
    private Long buyerId;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private String phoneNumber;
    private String amount;
}
