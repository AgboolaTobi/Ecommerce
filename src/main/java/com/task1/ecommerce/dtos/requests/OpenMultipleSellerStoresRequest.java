package com.task1.ecommerce.dtos.requests;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OpenMultipleSellerStoresRequest {
    private String sellerEmail;
    private Long sellerId;
    private String storeName;
    private String storeDescription;
}
