package com.example.auctionarena.service;

import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.Wish;
import java.security.Principal;
import java.util.List;

public interface WishService {
  WishDto getRow(Long pno, Long mid);

  Long addWish(WishDto wishdDto);

  Long removeWish(WishDto wishdDto);

  public default Wish dtoToEntity(WishDto wishDto) {
    return Wish
        .builder()
        .product(Product.builder().pno(wishDto.getPno()).build())
        .member(Member.builder().mid(wishDto.getMid()).build())
        .build();
  }

  public default WishDto entityToDto(Wish wish) {
    return WishDto
        .builder()
        .wno(wish.getWno())
        .pno(wish.getProduct().getPno())
        .mid(wish.getMember().getMid())
        .build();
  }
}
