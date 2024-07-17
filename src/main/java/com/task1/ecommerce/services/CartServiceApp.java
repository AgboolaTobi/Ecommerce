package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Cart;
import com.task1.ecommerce.data.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class CartServiceApp implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

}