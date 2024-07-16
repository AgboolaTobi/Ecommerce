package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
