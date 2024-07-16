package com.task1.ecommerce.services;

import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.SellerRegistrationException;

public interface SellerService {
    SellerRegistrationResponse registerSeller(SellerRegistrationRequest request) throws SellerRegistrationException;
}
