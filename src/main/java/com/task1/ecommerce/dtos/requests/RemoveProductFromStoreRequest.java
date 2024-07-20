package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveProductFromStoreRequest {
    private Long sellerId;
    private Long storeId;
    private Long productId;
}
