package com.task1.ecommerce.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ViewAllProductsInStoreRequest {
    private Long sellerId;
    private Long storeId;
}
