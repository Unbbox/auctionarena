package com.example.auctionarena.service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.BiddingRepository;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class BiddingServiceImpl implements BiddingService {

  private final BiddingRepository repository;

  @Override
  public List<BiddingDto> getBiddingList(Long pno) {
    log.info("{}번 제품 응찰 리스트 가져오기", pno);

    Product product = Product.builder().pno(pno).build();
    log.info("bid pno : {}", product);
    List<Bidding> biddings = repository.findByProductOrderByCreatedDateDesc(
      product
    );
    log.info("bid list : {}", biddings);

    Function<Bidding, BiddingDto> fn = bidding -> entityToDto(bidding);
    return biddings.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public Long bidRegister(BiddingDto biddingDto) {
    log.info("입찰 등록 {}", biddingDto);

    Bidding bidding = dtoToEntity(biddingDto);
    return repository.save(bidding).getBno();
  }

  @Override
  public BiddingDto getBestBidding(Long pno) {
    log.info("{}번 응찰 기록 조회", pno);

    Product product = Product.builder().pno(pno).build();
    Bidding bidding = repository.findTop1ByProductOrderByBiddingPriceDesc(
      product
    );

    if (bidding == null) {
      Member member = Member.builder().mid(0L).build();

      bidding =
        Bidding
          .builder()
          .biddingPrice(0L)
          .product(product)
          .member(member)
          .build();
    }
    return entityToDto(bidding);
  }

  // 추가부분
  @Override
  public List<Long> getBiddingPno(Long mid) {
    List<Long> biddings = repository.findBybiddingPno(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<BiddingDto> getMybidPrice(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPrice(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  // 카테고리 1 입찰
  @Override
  public List<Long> getBiddingPnoCno(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<Long> getBiddingPnoCno2(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno2(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<Long> getBiddingPnoCno3(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno3(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<Long> getBiddingPnoCno4(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno4(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<Long> getBiddingPnoCno5(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno5(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  @Override
  public List<Long> getBiddingPnoCno6(Long mid) {
    List<Long> biddings = repository.findBybiddingPnoCno6(mid);
    log.info("biddingService biddings : {}", biddings);

    return biddings;
  }

  // 카테고리 1 내 입찰금액
  @Override
  public List<BiddingDto> getMybidPriceCno(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMybidPriceCno2(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno2(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMybidPriceCno3(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno3(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMybidPriceCno4(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno4(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMybidPriceCno5(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno5(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMybidPriceCno6(Long mid) {
    List<Bidding> bidding = repository.findBymybiddingPriceCno6(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public List<BiddingDto> getMypayPrice(Long mid) {
    List<Bidding> bidding = repository.findBymypaymentPrice(mid);
    Function<Bidding, BiddingDto> fn = biddings -> entityToDto2(biddings);
    return bidding.stream().map(fn).collect(Collectors.toList());
  }
}
