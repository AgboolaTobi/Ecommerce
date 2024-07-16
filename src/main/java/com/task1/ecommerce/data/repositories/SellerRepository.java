package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
