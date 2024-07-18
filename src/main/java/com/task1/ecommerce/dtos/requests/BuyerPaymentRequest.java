package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter

public class BuyerPaymentRequest {
    private Long buyerId;
    private Long orderId;
    private BigDecimal amount;
}
