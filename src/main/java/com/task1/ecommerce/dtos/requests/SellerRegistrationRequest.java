package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SellerRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String storeName;
    private String storeDescription;
}
