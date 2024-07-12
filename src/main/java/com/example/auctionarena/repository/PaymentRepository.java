package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}
