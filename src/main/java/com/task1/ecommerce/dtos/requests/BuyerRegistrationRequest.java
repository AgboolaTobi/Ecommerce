package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyerRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
}
