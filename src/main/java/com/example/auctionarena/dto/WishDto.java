package com.example.auctionarena.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WishDto {

    private Long wno;

    // 제품
    private Long productPno;

    // 멤버
    private Long MemberMid;
}
