package com.example.auctionarena.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.service.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MypageService service;

    @GetMapping("/wish/{mid}")
    public Long getMethodName(@PathVariable("mid") Long mid) {
        log.info("{}번 멤버 찜 갯수 요청", mid);

        return service.getWishCnt(mid);
    }

}
