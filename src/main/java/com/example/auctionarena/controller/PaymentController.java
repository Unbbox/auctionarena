package com.example.auctionarena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.dto.PaymentDto;
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
    public ResponseEntity<Long> postPayment(@RequestBody PaymentDto paymentDto) {
        log.info("결제 요청 {}", paymentDto);

        // 클라이언트에게 저장된 paymentId를 JSON 형식으로 응답
        return new ResponseEntity<Long>(paymentService.savePayment(paymentDto), HttpStatus.OK);
    }
}
