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
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.SellerRegistrationException;

public interface SellerService {
    SellerRegistrationResponse registerSeller(SellerRegistrationRequest request) throws SellerRegistrationException;

    OpenMultipleSellerStoresResponse openMoreStore(OpenMultipleSellerStoresRequest request) throws SellerNotFoundException;

    Seller findByEmail(String sellerEmail);

    void save(Seller existingSeller);

    SellerLoginResponse login(SellerLoginRequest request) throws SellerNotFoundException;

    SellerLogoutResponse logout(SellerLogoutRequest request) throws SellerNotFoundException;
}
