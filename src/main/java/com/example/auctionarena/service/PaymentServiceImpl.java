package com.example.auctionarena.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.PaymentDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Payment;
import com.example.auctionarena.repository.BiddingRepository;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final BiddingRepository biddingRepository;
    
    public Long savePayment(PaymentDto paymentDto) {
        log.info("db 저장 요청 {}", paymentDto);

        Optional<Member> optionalMember = memberRepository.findById(paymentDto.getMno());
        Optional<Bidding> optionalBidding = biddingRepository.findById(paymentDto.getBno());

        Member member = optionalMember.get();
        Bidding bidding = optionalBidding.get();

        // PaymentRequest에서 필요한 데이터를 추출하여 Payment 엔티티에 매핑하고 저장합니다.
        // PaymentDto에서 필요한 데이터를 추출하여 Payment 엔티티에 매핑하고 저장합니다.
        Payment payment = Payment.builder()
                .status(paymentDto.getStatus())
                .member(member)
                .bidding(bidding)
                .impUid(paymentDto.getImpUid())
                .merchantUid(paymentDto.getMerchantUid())
                .build();

        return paymentRepository.save(payment).getId();
    }
}
