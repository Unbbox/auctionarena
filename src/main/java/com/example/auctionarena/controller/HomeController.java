package com.example.auctionarena.controller;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.dto.ProductImageDto;
import com.example.auctionarena.entity.Product;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    model.addAttribute("list", service.descList(pno));
    // model.addAttribute("biddinglist", biddinglist);
    return "/index";
  }

  @ResponseBody
  @GetMapping("auth")
  public Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();

    return authentication;
  }
}
