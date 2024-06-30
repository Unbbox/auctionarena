package com.example.auctionarena.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.entity.Wish;
import com.example.auctionarena.repository.WishRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class WishServiceImpl implements WishService {

    private final WishRepository repository;

    @Override
    public Long addWish(WishDto wishDto) {

        log.info("{}번 위시리스트에 추가", wishDto.getProductPno());

        // WishDto wishDto = WishDto.builder()
        // .mid(mid)
        // .pno(pno)
        // .build();

        Wish wish = dtoToEntity(wishDto);

        repository.save(wish);

        return wish.getWno();
    }

    @Override
    public Long removeWish(WishDto wishDto) {
        log.info("{}번 위시리스트에 제거", wishDto.getProductPno());

        Wish wish = dtoToEntity(wishDto);

        repository.delete(wish);

        return wish.getWno();
    }

    @Override
    public List<WishDto> getRow(Long pno) {
        log.info("위시리스트 가져오기");
        
        List<Wish> wishs = repository.findByProductPno(pno);
    

        log.info("wish : {}", repository.findByProductPno(pno));
        // WishDto wishDto = new WishDto();
        // wishDto.setPno(pno);
        // wishDto.setWno(pno);
        // wishDto.setMid(pno);
        // repository.findByProductId(pno).get;

        // return wishDto;
        return wishs.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
    }
    
}
