package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
