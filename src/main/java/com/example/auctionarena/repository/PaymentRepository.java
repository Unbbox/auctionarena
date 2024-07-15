package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.auctionarena.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
    @Query("select p from Payment p where p.bidding.bno = :bno")
    Payment findByBno(Long bno);   
}
