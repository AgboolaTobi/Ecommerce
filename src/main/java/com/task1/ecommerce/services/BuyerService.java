package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Buyer;
import com.task1.ecommerce.dtos.requests.*;
import com.task1.ecommerce.dtos.responses.*;
import com.task1.ecommerce.exceptions.*;

public interface BuyerService {

    BuyerRegistrationResponse registerBuyer(BuyerRegistrationRequest request) throws BuyerExistException, BuyerRegistrationException;


    AddToCartResponse addProductToCart(AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException;

    OrderResponse makeOrder(OrderRequest request) throws BuyerNotFoundException, EmptyCartException;

    Buyer findByBuyerId(Long buyerId);

    void save(Buyer existingBuyer);

    BuyerLoginResponse buyerLogin(BuyerLoginRequest request) throws BuyerNotFoundException, InvalidCredentialsException;

    BuyerLogoutResponse logoutBuyer(BuyerLogoutRequest request) throws BuyerNotFoundException;

    RemoveProductFromCartResponse removeProductFromCart(RemoveProductFromCartRequest request) throws BuyerNotFoundException, CartItemException;
}
