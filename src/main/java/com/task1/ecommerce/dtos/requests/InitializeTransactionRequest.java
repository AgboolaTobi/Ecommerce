package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializeTransactionRequest {

    private String email;
    private String amount;
}
