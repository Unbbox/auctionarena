package com.example.auctionarena.dto;

import java.time.LocalDateTime;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentDto {

    private Long id;

    // 결제 상태
    private Boolean status = false;

    // 결제 시간 - baseentity
    private LocalDateTime paidAt;

    // 결제가격 - 최고 응찰가
    private Integer price;

    // 상품이름
    private String pname;

    // 회원번호
    private Long mno;
}
