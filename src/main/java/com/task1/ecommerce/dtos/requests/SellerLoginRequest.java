package com.task1.ecommerce.dtos.requests;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SellerLoginRequest {
    private String email;
    private String password;
}
