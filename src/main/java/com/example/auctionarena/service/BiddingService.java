package com.example.auctionarena.service;

import java.util.List;
import java.util.Map;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;

public interface BiddingService {

    // 응찰 리스트 가져오기
    List<BiddingDto> getBiddingList(Long pno);

    // 입찰 등록
    Long bidRegister(BiddingDto biddingDto);

    // entityToDto
    public default BiddingDto entityToDto(Bidding bidding) {

        return BiddingDto.builder()
                .biddingPrice(bidding.getBiddingPrice())
                .biddingTime(bidding.getCreatedDate())
                .pno(bidding.getProduct().getPno())
                .mid(bidding.getMember().getMid())
                .mNickName(bidding.getMember().getNickname())
                .build();
    }

    // dtoToEntity
    public default Bidding dtoToEntity(BiddingDto dto) {

        Bidding bidding = new Bidding();
        bidding.setBiddingPrice(dto.getBiddingPrice());
        bidding.setMember(Member.builder().mid(dto.getMid()).build());
        bidding.setProduct(Product.builder().pno(dto.getPno()).build());
        return bidding;
    }
}
