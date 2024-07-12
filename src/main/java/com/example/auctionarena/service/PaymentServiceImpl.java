package com.example.auctionarena.service;

import org.springframework.stereotype.Service;

import com.example.auctionarena.entity.Payment;
import com.example.auctionarena.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    
    public Long savePayment(Payment paymentRequest) {
        // PaymentRequest에서 필요한 데이터를 추출하여 Payment 엔티티에 매핑하고 저장합니다.
        Payment payment = Payment.builder()
            .status(true)
            .member(paymentRequest.getMember())
            .bidding(paymentRequest.getBidding())
            .build();

        return paymentRepository.save(payment).getId();
    }
}
