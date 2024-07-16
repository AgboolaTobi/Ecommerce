package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.BuyerOder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerOrderRepository extends JpaRepository<BuyerOder, Long> {
}
