package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductIdAndId(Long id, Long cart);

}
