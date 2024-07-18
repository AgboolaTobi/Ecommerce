package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.BuyerOder;

public interface BuyerOrderService {


    void save(BuyerOder buyerOrder);

    BuyerOder findById(Long orderId);
}
