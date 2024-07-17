package com.task1.ecommerce.services;

import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.requests.BuyerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.dtos.responses.BuyerRegistrationResponse;
import com.task1.ecommerce.exceptions.BuyerExistException;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;

public interface BuyerService {
    BuyerRegistrationResponse registerBuyer(BuyerRegistrationRequest request) throws BuyerExistException;


    AddToCartResponse addProductToCart(AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException;
}
