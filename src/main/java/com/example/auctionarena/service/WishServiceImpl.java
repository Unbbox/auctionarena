package com.example.auctionarena.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.Wish;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.ProductRepository;
import com.example.auctionarena.repository.WishRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class WishServiceImpl implements WishService {

    private final WishRepository repository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long addWish(WishDto wishDto) {

        log.info("{}번 위시리스트에 추가", wishDto.getPno());

        // WishDto wishDto = WishDto.builder()
        // .mid(mid)
        // .pno(pno)
        // .build();

        Wish wish = dtoToEntity(wishDto);

        repository.save(wish);

        return wish.getWno();
    }

    @Transactional
    @Override
    public Long removeWish(WishDto wishDto) {
        log.info("{}번 위시리스트 제거", wishDto.getPno());

        Product product = Product.builder().pno(wishDto.getPno()).build();
        Member member = Member.builder().mid(wishDto.getMid()).build();

        Wish wish = repository.findByProductAndMember(product, member);
        log.info("제거 wish : {}", wish);

        repository.delete(wish);

        return wish.getWno();
    }

    @Override
    public WishDto getRow(Long pno, Long mid) {
        log.info("{}번 제품 위시리스트 가져오기", pno);

        Product product = Product.builder().pno(pno).build();
        Member member = Member.builder().mid(mid).build();

        Wish wish = repository.findByProductAndMember(product, member);

        if (wish != null) {
            log.info("wish list : {}", wish);
    
            WishDto dto = entityToDto(wish);
            log.info("wishDto : {}", dto);
    
            return entityToDto(wish);
        } else {
            return null;
        }
        
    }

}
