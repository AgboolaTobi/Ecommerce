package com.task1.ecommerce.services;


import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ExistingProductException;
}
