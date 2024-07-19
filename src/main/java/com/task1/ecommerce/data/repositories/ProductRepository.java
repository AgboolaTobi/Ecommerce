package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Category;
import com.task1.ecommerce.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductByName(String name);

    List<Product> getByProductCategory(Category request);
}
