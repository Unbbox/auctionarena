package com.example.auctionarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("auctionArena")
@Log4j2
@Controller
public class AuctionController {

    // 전체 상품
    @GetMapping("/categories")
    public String getCategory() {
        log.info("전체 상품 목록 페이지 요청");

        return "auctionArena/categories";
    }

    // 판매 등록
    @GetMapping("/product_details")
    public void getDetails() {
        log.info("물품 판매 페이지 요청");
    }

    // @GetMapping("/categories")
    // public void getCategory() {
    // //
    // }

}
