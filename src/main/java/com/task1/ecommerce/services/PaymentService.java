package com.task1.ecommerce.services;

import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.OrderNotFoundException;

public interface PaymentService {
    BuyerPaymentResponse makePayment(BuyerPaymentRequest request) throws BuyerNotFoundException, OrderNotFoundException;
}
