package com.example.auctionarena.service;

import org.springframework.stereotype.Service;

import com.example.auctionarena.repository.WishRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService {

    private final WishRepository wishRepository;

    @Override
    public Long getWishCnt(Long mid) {
        log.info("{}멤버 위시리스트 갯수 불러오기", mid);
        return wishRepository.findByMemberMid(mid);
    }

}
