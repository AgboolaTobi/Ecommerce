package com.task1.ecommerce.controllers;

import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.OrderNotFoundException;
import com.task1.ecommerce.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment/")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("makePayment")
    public ResponseEntity<BuyerPaymentResponse> makePayment(@RequestBody BuyerPaymentRequest request) throws OrderNotFoundException, BuyerNotFoundException {
     return new ResponseEntity<>(paymentService.makePayment(request), HttpStatus.CREATED);
    }
}
