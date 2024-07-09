package com.example.auctionarena.service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.entity.Bidding;
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
        product);
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
        product);

    if (bidding == null) {
      Member member = Member.builder()
          .mid(0L)
          .build();

      bidding = Bidding.builder()
          .biddingPrice(0L)
          .product(product)
          .member(member)
          .build();
    }
    return entityToDto(bidding);
  }
}
