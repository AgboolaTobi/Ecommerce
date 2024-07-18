package com.task1.ecommerce.services;

import com.task1.ecommerce.configs.PayStackConfig;
import com.task1.ecommerce.data.models.Buyer;
import com.task1.ecommerce.data.models.BuyerOder;
import com.task1.ecommerce.data.models.Payment;
import com.task1.ecommerce.data.repositories.PaymentRepsitory;
import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.requests.InitializeTransactionRequest;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.dtos.responses.PayStackTransactionResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceApp implements PaymentService{

    private final BuyerService buyerService;
    private final BuyerOrderService buyerOrderService;
    private final PaymentRepsitory paymentRepsitory;
    private final PayStackConfig config;

    @Override
    public BuyerPaymentResponse makePayment(BuyerPaymentRequest request) throws BuyerNotFoundException, OrderNotFoundException {

        Buyer existingBuyer = buyerService.findByBuyerId(request.getBuyerId());
        if (existingBuyer == null) throw new BuyerNotFoundException("Buyer not found");

        BuyerOder targetOrder = buyerOrderService.findById(request.getOrderId());

        if (targetOrder == null) throw new OrderNotFoundException("Order not found");

        List<Payment> buyerPayments = existingBuyer.getPayments();
        Payment payment = new Payment();
        payment.setBuyerId(existingBuyer.getId());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        buyerPayments.add(payment);
        existingBuyer.setPayments(buyerPayments);

        buyerService.save(existingBuyer);
        paymentRepsitory.save(payment);



        BuyerPaymentResponse response = new BuyerPaymentResponse();
        response.setMessage("Successfully made payment for OrderId: " + request.getOrderId());

        RestTemplate template = new RestTemplate();
        HttpEntity<InitializeTransactionRequest> transactionRequest = buildPayment(existingBuyer, payment);

        ResponseEntity<PayStackTransactionResponse> transactionResponseResponse =
                template.postForEntity(config.getPayStackBaseUrl(),transactionRequest, PayStackTransactionResponse.class);

        System.out.println(transactionResponseResponse.getBody().getTransactionDetails().getAuthorizationUrl());

        return response;

    }

    private HttpEntity<InitializeTransactionRequest> buildPayment(Buyer existingBuyer, Payment payment) {
        InitializeTransactionRequest request = new InitializeTransactionRequest();
        request.setEmail(existingBuyer.getEmail());
        request.setAmount(String.valueOf(payment.getAmount().multiply(BigDecimal.valueOf(100))));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + config.getPayStackApiKey());
        return new HttpEntity<>(request, headers);
    }
}
