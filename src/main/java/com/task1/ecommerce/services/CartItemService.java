package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.CartItem;

import java.util.List;


public interface CartItemService {

    void save(CartItem cartItem);

    List<CartItem> findByProductId(Long id);
}
