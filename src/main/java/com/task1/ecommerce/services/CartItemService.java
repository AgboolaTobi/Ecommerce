package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.CartItem;



public interface CartItemService {


    CartItem findByProductIdAndId(Long id, Long cartId);

    void save(CartItem cartItem);
}
