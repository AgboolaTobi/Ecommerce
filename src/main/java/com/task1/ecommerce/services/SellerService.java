package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.dtos.requests.OpenMultipleSellerStoresRequest;
import com.task1.ecommerce.dtos.requests.SellerLoginRequest;
import com.task1.ecommerce.dtos.requests.SellerLogoutRequest;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.OpenMultipleSellerStoresResponse;
import com.task1.ecommerce.dtos.responses.SellerLoginResponse;
import com.task1.ecommerce.dtos.responses.SellerLogoutResponse;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.InvalidCredentialsException;
import com.task1.ecommerce.exceptions.InvalidPhoneNumberException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.SellerRegistrationException;

public interface SellerService {
    SellerRegistrationResponse registerSeller(SellerRegistrationRequest request) throws SellerRegistrationException, InvalidPhoneNumberException;

    OpenMultipleSellerStoresResponse openMoreStore(OpenMultipleSellerStoresRequest request) throws SellerNotFoundException;

    Seller findByEmail(String sellerEmail);

    void save(Seller existingSeller);

    SellerLoginResponse sellerLogin(SellerLoginRequest request) throws SellerNotFoundException, InvalidCredentialsException;

    SellerLogoutResponse sellerLogout(SellerLogoutRequest request) throws SellerNotFoundException;

    Seller findSellerbyId(Long sellerId);
}
