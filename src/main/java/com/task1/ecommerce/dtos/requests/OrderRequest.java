package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {
    private Long buyerId;
    private String deliveryAddress;
    private String phoneNumber;
    private String amount;
    private String preferredDeliveryDate;

}
