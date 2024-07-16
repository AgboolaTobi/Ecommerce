package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {
}
