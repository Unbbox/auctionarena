package com.example.auctionarena.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BiddingDto {

    private Long bno;

    private Long pno; // 제품 번호

    private Long mid; // 멤버 번호

    private Long biddingPrice;

    private Long biddingTime;
}
