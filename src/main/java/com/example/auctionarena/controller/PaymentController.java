package com.example.auctionarena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.entity.Payment;
import com.example.auctionarena.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<Long> processPayment(@RequestBody Payment paymentRequest) {
        log.info("결제 요청 {}", paymentRequest);
        // Payment는 JavaScript에서 전송한 결제 정보를 담고 있는 DTO입니다.
        Long payment = paymentService.savePayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }
}
