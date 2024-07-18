package com.task1.ecommerce.PaymentServiceTests;


import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.OrderNotFoundException;
import com.task1.ecommerce.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;


    @Test
    public void testThatPaymentCanBeMadeForAPurchaseOrderByTheBuyer() throws OrderNotFoundException, BuyerNotFoundException {
        BuyerPaymentRequest request = new BuyerPaymentRequest();
        request.setBuyerId(1L);
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(2000));

        BuyerPaymentResponse response = paymentService.makePayment(request);

        assertThat(response).isNotNull();


    }
}
