package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.CartItem;
import com.task1.ecommerce.data.repositories.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CartItemServiceApp implements CartItemService{

    private final CartItemRepository cartItemRepository;

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByProductId(Long id) {
        return new ArrayList<>(cartItemRepository.findAll());
    }
}
