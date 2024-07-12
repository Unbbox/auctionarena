package com.example.auctionarena.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Payment extends BaseEntity{
    
    @SequenceGenerator(name= "payment_seq_gen", sequenceName = "payment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq_gen")
    @Id
    private Long id;

    // 결제 상태
    private Boolean status = false;

    // 결제 시간 - baseentity

    // 회원 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 결제가, 상품아이디.이름
    @OneToOne(fetch = FetchType.LAZY)
    private Bidding bidding;
}
