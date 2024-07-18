package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.BuyerOder;
import com.task1.ecommerce.data.repositories.BuyerOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BuyerOderServiceApp implements BuyerOrderService{

    private final BuyerOrderRepository buyerOrderRepository;
    @Override
    public void save(BuyerOder buyerOrder) {
        buyerOrderRepository.save(buyerOrder);
    }
}
