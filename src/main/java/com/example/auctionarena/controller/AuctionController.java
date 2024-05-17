package com.example.auctionarena.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("auctionArena")
@Log4j2
@Controller
public class AuctionController {

  // 전체 상품
  @GetMapping("/categories")
  public String getAllCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/categories";
  }

  @GetMapping("/fashion-category")
  public String getFashionCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/fashion-category";
  }

  @GetMapping("/mobile-category")
  public String getMobileCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/mobile-category";
  }

  @GetMapping("/electronic-category")
  public String getElectronicCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/electronic-category";
  }

  @GetMapping("/game-category")
  public String getGameCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/game-category";
  }

  @GetMapping("/trib-category")
  public String getTribCategory() {
    log.info("전체 상품 목록 페이지 요청");

    return "auctionArena/trib-category";
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
