package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  @Query("select p from Payment p where p.bidding.bno = :bno")
  Payment findByBno(Long bno);

  @Query(
    value = "select p2.PNO,p.CREATED_DATE FROM PAYMENT p LEFT JOIN bidding b ON p.BIDDING_BNO = b.BNO LEFT JOIN PRODUCT p2 ON p2.pno = b.PRODUCT_PNO  WHERE p.MEMBER_MID = ?1 ORDER BY p.CREATED_DATE desc",
    nativeQuery = true
  )
  List<Long> findByMemberMid(Long mid);
}
