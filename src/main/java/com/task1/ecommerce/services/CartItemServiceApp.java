package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.CartItem;
import com.task1.ecommerce.data.repositories.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class CartItemServiceApp implements CartItemService{

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem findByProductIdAndId(Long id, Long cartId) {
        return cartItemRepository.findByProductIdAndId(id, cartId);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
}
