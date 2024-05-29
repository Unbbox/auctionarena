package com.example.auctionarena.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.BiddingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BiddingServiceImpl implements BiddingService {

    private final BiddingRepository repository;

    @Override
    public List<BiddingDto> getBiddingList(Long pno) {
        log.info("{}번 제품 응찰 리스트 가져오기", pno);

        Product product = Product.builder().pno(pno).build();
        List<Bidding> biddings = repository.findByProductOrderByCreatedDateDesc(product);

        Function<Bidding, BiddingDto> fn = bidding -> entityToDto(bidding);
        return biddings.stream().map(fn).collect(Collectors.toList());
    }

    @Override
    public Long bidRegister(BiddingDto biddingDto) {
        log.info("입찰 등록 {}", biddingDto);

        Bidding bidding = dtoToEntity(biddingDto);
        return repository.save(bidding).getBno();
    }
}
