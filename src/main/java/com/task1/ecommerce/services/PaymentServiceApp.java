package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Buyer;
import com.task1.ecommerce.data.models.BuyerOder;
import com.task1.ecommerce.data.models.Payment;
import com.task1.ecommerce.data.repositories.PaymentRepsitory;
import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceApp implements PaymentService{

    private final BuyerService buyerService;
    private final BuyerOrderService buyerOrderService;
    private final PaymentRepsitory paymentRepsitory;

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
        paymentRepsitory.save(payment);

        buyerPayments.add(payment);
        existingBuyer.setPayments(buyerPayments);

        buyerService.save(existingBuyer);

        BuyerPaymentResponse response = new BuyerPaymentResponse();
        response.setMessage("Successfully made payment for OrderId: " + request.getOrderId());
        return response;

    }
}
