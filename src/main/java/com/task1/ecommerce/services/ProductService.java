package com.task1.ecommerce.services;


import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request);
}
