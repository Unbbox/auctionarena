package com.example.auctionarena.controller;

import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.service.DetailService;
import com.example.auctionarena.service.ProductService;
import jakarta.validation.Valid;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
      Model model) {
    log.info("전체 상품 목록 페이지 요청");

    model.addAttribute("result", service.getList(requestDto));
    // return "auctionArena/categories";
  }

  // 제품 상세 페이지
  @GetMapping("/product_details")
  public void getDetails(
      @RequestParam Long pno,
      Model model,
      @ModelAttribute("requestDto") CategoryPageRequestDto requestDto) {
    log.info("제품 상세 페이지 요청 {}", pno);

    ProductDto dto = detailService.getRow(pno);
    log.info("제품 상세 출력 >> {}", dto);

    model.addAttribute("dto", detailService.getRow(pno));
  }

  // 제품 판매 등록 페이지
  @GetMapping("/product_sale")
  public void getProductSale(
      ProductDto productDto,
      Model model,
      @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
    log.info("제품 판매 페이지 요청");

    // 카테고리 리스트 보여주기
    model.addAttribute("categories", detailService.categoryNameList());
  }

  // 제품 판매 등록 POST
  @PostMapping("/product_sale")
  public String postMethodName(
      // @Valid // 나중에 다 완료 되면 추가
      ProductDto productDto,
      @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
      RedirectAttributes rttr) {
    log.info("제품 판매 등록 {}", productDto);

    Long pno = detailService.productRegister(productDto);

    rttr.addFlashAttribute("msg", pno);
    rttr.addAttribute("page", pageRequestDto.getPage());
    rttr.addAttribute("size", pageRequestDto.getSize());
    rttr.addAttribute("type", pageRequestDto.getType());
    rttr.addAttribute("keyword", pageRequestDto.getKeyword());

    return "redirect:/auctionArena/categories";
  }
}
