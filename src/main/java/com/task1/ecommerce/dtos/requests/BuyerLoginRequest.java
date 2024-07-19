package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BuyerLoginRequest {
    private String email;
    private String password;
}
