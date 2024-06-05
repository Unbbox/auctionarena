package com.example.auctionarena.controller;

import com.example.auctionarena.dto.ProductDto;

import com.example.auctionarena.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
public class HomeController {

  private final ProductService service;

  // private final ProductImageService service;

  @GetMapping("/")
  public String Home(Model model, Long pno) {
    log.info("메인화면 요청");
    // List<ProductDto> biddinglist = service.BiddingDescList();
    List<ProductDto> productDtos = service.pnodescList();
    for (ProductDto productDto : productDtos) {
      log.info(productDto);
      log.info("============");
    }
    model.addAttribute("list", service.pnodescList());
    model.addAttribute("biddinglist", service.BiddingDescList());
    return "/index";
  }

  // Access 페이지
  @GetMapping("access-denied")
  public void getAccess() {
    log.info("access deniend");
  }

  // 에러페이지
  // @GetMapping("error")
  // public String getError() {
  // log.info("error");
  // return "except/url404";
  // }

  @ResponseBody
  @GetMapping("auth")
  public Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();

    return authentication;
  }
}
