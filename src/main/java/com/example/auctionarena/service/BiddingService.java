package com.example.auctionarena.service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import java.util.List;
import java.util.Map;

public interface BiddingService {
  // 응찰 리스트 가져오기
  List<BiddingDto> getBiddingList(Long pno);

  // 응찰 최고기록 가져오기
  BiddingDto getBestBidding(Long pno);

  // 입찰 등록
  Long bidRegister(BiddingDto biddingDto);

  List<Long> getBiddingPno(Long mid);
  // 카테고리별 입찰
  List<Long> getBiddingPnoCno(Long mid);
  List<Long> getBiddingPnoCno2(Long mid);
  List<Long> getBiddingPnoCno3(Long mid);
  List<Long> getBiddingPnoCno4(Long mid);
  List<Long> getBiddingPnoCno5(Long mid);
  List<Long> getBiddingPnoCno6(Long mid);

  List<BiddingDto> getMybidPrice(Long mid);

  // 카테고리별 내 입찰금액
  List<BiddingDto> getMybidPriceCno(Long mid);
  List<BiddingDto> getMybidPriceCno2(Long mid);
  List<BiddingDto> getMybidPriceCno3(Long mid);
  List<BiddingDto> getMybidPriceCno4(Long mid);
  List<BiddingDto> getMybidPriceCno5(Long mid);
  List<BiddingDto> getMybidPriceCno6(Long mid);
  List<BiddingDto> getMypayPrice(Long mid);

  // 필요없을수도
  // BiddingDto getSaleBidList(Long mid);

  // List<Long> getMyBiddingPrice(Long mid);

  // entityToDto
  public default BiddingDto entityToDto(Bidding bidding) {
    return BiddingDto
      .builder()
      .biddingPrice(
        bidding.getBiddingPrice() != null ? bidding.getBiddingPrice() : 0L
      )
      .biddingTime(bidding.getCreatedDate())
      .pno(bidding.getProduct().getPno())
      .mid(bidding.getMember().getMid())
      .bno(bidding.getBno())
      // .createdDate(bidding.getPayment().getCreatedDate())
      // .status(bidding.getPayment().getStatus())
      .mNickName(bidding.getMember().getNickname())
      .build();
  }

  public default BiddingDto entityToDto2(Bidding bidding) {
    return BiddingDto
      .builder()
      .biddingPrice(
        bidding.getBiddingPrice() != null ? bidding.getBiddingPrice() : 0L
      )
      .biddingTime(bidding.getCreatedDate())
      .pno(bidding.getProduct().getPno())
      .mid(bidding.getMember().getMid())
      .bno(bidding.getBno())
      .createdDate(bidding.getPayment().getCreatedDate())
      .status(bidding.getPayment().getStatus())
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
