package com.task1.ecommerce.data.repositories;

import com.task1.ecommerce.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepsitory extends JpaRepository<Payment, Long> {
}
