package com.example.auctionarena.service;

import com.example.auctionarena.dto.PaymentDto;
import com.example.auctionarena.entity.Payment;
import java.util.List;

public interface PaymentService {
  Long savePayment(PaymentDto paymentDto);

  List<Long> getPaymentMid(Long mid);

  public default PaymentDto entityToDto(Payment payment) {
    return PaymentDto
      .builder()
      .status(payment.getStatus())
      .impUid(payment.getImpUid())
      .merchantUid(payment.getMerchantUid())
      .bno(payment.getBidding().getBno())
      .createdDate(payment.getCreatedDate())
      .build();
  }
}
