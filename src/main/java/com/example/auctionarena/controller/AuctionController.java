package com.example.auctionarena.controller;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.service.DetailService;
import com.example.auctionarena.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("auctionArena")
@Log4j2
@Controller
@RequiredArgsConstructor
public class AuctionController {

  private final ProductService service;
  private final DetailService detailService;

  // 전체 상품
  @GetMapping("/categories")
  public void getAllCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("전체 상품 목록 페이지 요청");

    model.addAttribute("result", service.getList(requestDto));
    // return "auctionArena/categories";
  }

  @GetMapping("/fashion-category")
  public void getFashionCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("전체 상품 목록 페이지 요청");
    model.addAttribute("result", service.getList(requestDto));
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

  // 제품 상세 페이지
  @GetMapping("/product_details")
  public void getDetails(
    @RequestParam Long pno,
    Model model,
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto
  ) {
    log.info("제품 상세 페이지 요청 {}", pno);

    ProductDto dto = detailService.getRow(pno);
    log.info("제품 상세 출력 >> {}", dto);

    model.addAttribute("dto", detailService.getRow(pno));
  }

  // 제품 판매 등록 페이지
  @GetMapping("/product_sale")
  public void getProductSale() {
    log.info("제품 판매 페이지 요청");
  }
}
