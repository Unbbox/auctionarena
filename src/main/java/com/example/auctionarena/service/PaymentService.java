package com.example.auctionarena.service;

import com.example.auctionarena.entity.Payment;

public interface PaymentService {
    Long savePayment(Payment paymentRequest);
}
