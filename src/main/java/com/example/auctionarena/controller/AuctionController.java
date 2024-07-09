package com.example.auctionarena.controller;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.service.BiddingService;
import com.example.auctionarena.service.DetailService;
import com.example.auctionarena.service.ProductService;
import com.example.auctionarena.service.WishService;
import jakarta.validation.Valid;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
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
  private final BiddingService biddingService;
  private final WishService wishService;

  // 전체 상품
  @GetMapping("/categories")
  public void getAllCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("전체 상품 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("result", service.getList(requestDto));
    log.info("result {}", service.getList(requestDto));
  }

  @GetMapping("/bidding_list")
  public void getbiddingList(Model model, Principal principal) {
    // log.info(
    //   "bid_list : {}",
    //   service.MemberBiddingList(principal.getName(), null)
    // );
    model.addAttribute(
      "bid_list",
      service.MemberBiddingList(principal.getName())
    );
  }

  // 패션
  @GetMapping("/fashion-category")
  public void getfashionCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("fashion", service.getFashionList(requestDto));
    log.info("fashion {}", service.getFashionList(requestDto));
  }

  // 모바일
  @GetMapping("/mobile-category")
  public void getMobileCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("mobile", service.getMobileList(requestDto));
    log.info("mobile {}", service.getMobileList(requestDto));
  }

  // 가전제품
  @GetMapping("/electric-category")
  public void getelectronicCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("electric", service.getElectricList(requestDto));
    log.info("electric {}", service.getElectricList(requestDto));
  }

  // 게임
  @GetMapping("/game-category")
  public void getgameCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("game", service.getGameList(requestDto));
    log.info("game {}", service.getGameList(requestDto));
  }

  // 레저/여행
  @GetMapping("/trib-category")
  public void gettribCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("trib", service.getTribList(requestDto));
    log.info("trib {}", service.getTribList(requestDto));
  }

  // 기타
  @GetMapping("/etc-category")
  public void getetcCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("모바일 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("etc", service.getEtcList(requestDto));
    log.info("etc {}", service.getEtcList(requestDto));
  }

  // 제품 상세 페이지
  @GetMapping("/product_details")
  public void getDetails(
    @RequestParam Long pno,
    Model model,
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto
  ) {
    log.info("제품 상세 페이지 요청 {}", pno);

    // 제품 dto
    ProductDto dto = detailService.getRow(pno);
    model.addAttribute("dto", dto);

    // 관련 카테고리 제품 dto
    List<ProductDto> productDtos = detailService.getRelationList(pno);
    for (ProductDto productDto : productDtos) {
      log.info("product : ", productDto);
      log.info("===========");
    }

    // 찜목록 dto
    // List<WishDto> wishDto = wishService.getRow(wishDto);
    // model.addAttribute("wishDto", wishDto);

    // log.info("relation : ", detailService.getRelationList(pno));
    model.addAttribute("relationDto", detailService.getRelationList(pno));
  }

  // 제품 수정 페이지 Get
  @GetMapping("/product_modify")
  public void getProductModify(
    @RequestParam Long pno,
    Model model,
    @ModelAttribute("requestDto") PageRequestDto pageRequestDto
  ) {
    log.info("{}번 제품 정보", pno);

    model.addAttribute("dto", detailService.getRow(pno));
  }

  // 제품 수정 페이지 Post
  @PostMapping("/product_modify")
  public String postProductModify(
    ProductDto productDto,
    @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
    RedirectAttributes rttr
  ) {
    log.info("제품 수정 요청: {}", productDto);

    Long pno = detailService.productUpdate(productDto);
    log.info("제품 pno: {}", detailService.productUpdate(productDto));

    rttr.addFlashAttribute("msg", pno);
    rttr.addAttribute("pno", pno);
    rttr.addAttribute("page", pageRequestDto.getPage());
    rttr.addAttribute("type", pageRequestDto.getType());
    rttr.addAttribute("keyword", pageRequestDto.getKeyword());

    return "redirect:/auctionArena/product_details";
  }

  // 등록된 제품 삭제 페이지
  @PostMapping("/remove")
  public String postRemove(
    Long pno,
    @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
    RedirectAttributes rttr
  ) {
    log.info("{}번 제품 삭제 요청", pno);
    detailService.productRemove(pno);

    // rttr.addFlashAttribute("pno", pno);
    rttr.addFlashAttribute("pno", pno);
    log.info("pno : {}", pno);
    rttr.addAttribute("page", pageRequestDto.getPage());
    rttr.addAttribute("size", pageRequestDto.getSize());
    rttr.addAttribute("type", pageRequestDto.getType());
    rttr.addAttribute("keyword", pageRequestDto.getKeyword());
    return "redirect:/auctionArena/categories";
  }

  // 제품 판매 등록 페이지
  @GetMapping("/product_sale")
  public void getProductSale(
    ProductDto productDto,
    Model model,
    @ModelAttribute("requestDto") PageRequestDto pageRequestDto
  ) {
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
    // RedirectAttributes rttr) {
    RedirectAttributes rttr
  ) {
    log.info("제품 판매 등록 {}", productDto);

    Long pno = detailService.productRegister(productDto);

    rttr.addFlashAttribute("msg", pno);

    return "redirect:/auctionArena/categories";
  }

  // 고객센터
  @GetMapping("/customer-service")
  public void customerService() {
    log.info("고객센터 요청");
  }
}
