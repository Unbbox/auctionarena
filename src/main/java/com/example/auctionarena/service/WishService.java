package com.example.auctionarena.service;

import java.util.List;

import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.Wish;

public interface WishService {

    List<WishDto> getRow(Long pno);

    Long addWish(WishDto wishdDto);

    Long removeWish(WishDto wishdDto);

    public default Wish dtoToEntity(WishDto wishDto) {

        return Wish.builder()
                .product(Product.builder().pno(wishDto.getProductPno()).build())
                .member(Member.builder().mid(wishDto.getMemberMid()).build())
                .build();
    }

    public default WishDto entityToDto(Wish wish) {

        return WishDto.builder()
                .productPno(wish.getProduct().getPno())
                .memberMid(wish.getMember().getMid())
                .build();
    }
}
