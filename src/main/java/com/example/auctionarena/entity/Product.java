package com.example.auctionarena.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @Id
    private Long pno;

    private String title;

    private String content;

    private Long startPrice;

    private String biddingDate;

    // 멤버 관련
    // @ManyToOne(fetch = FetchType.LAZY)
    // private Member writer;

    // 응찰 관련
    @ManyToOne(fetch = FetchType.LAZY)
    private Bidding bidding;
}
