package com.example.auctionarena.controller;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Payment;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.PaymentRepository;
import com.example.auctionarena.service.BiddingService;
import com.example.auctionarena.service.CommentService;
import com.example.auctionarena.service.DetailService;
import com.example.auctionarena.service.MemberService;
import com.example.auctionarena.service.ProductService;
import com.example.auctionarena.service.WishService;
import jakarta.validation.Valid;
import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final CommentService commentService;
  private final MemberRepository memberRepository;
  private final MemberService memberService;
  private final PaymentRepository paymentRepository;

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

  // @GetMapping("/bidding_list")
  // public void getbiddingList(Model model, Principal principal) {
  // // log.info(
  // // "bid_list : {}",
  // // service.MemberBiddingList(principal.getName(), null)
  // // );
  // model.addAttribute(
  // "bid_list",
  // service.MemberBiddingList(principal.getName())
  // );
  // }

  // 패션
  @GetMapping("/fashion-category")
  public void getfashionCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("패션 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("fashion", service.getFashionList(requestDto));
    model.addAttribute("randomlist", service.RandomFashionList());
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
    model.addAttribute("randomlist", service.RandomMobileList());
    log.info("mobile {}", service.getMobileList(requestDto));
  }

  // 가전제품
  @GetMapping("/electric-category")
  public void getelectronicCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("가전제품 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("electric", service.getElectricList(requestDto));
    model.addAttribute("randomlist", service.RandomElectricList());
    log.info("electric {}", service.getElectricList(requestDto));
  }

  // 게임
  @GetMapping("/game-category")
  public void getgameCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("게임 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("game", service.getGameList(requestDto));
    model.addAttribute("randomlist", service.RandomGameList());
    log.info("game {}", service.getGameList(requestDto));
  }

  // 레저/여행
  @GetMapping("/trib-category")
  public void gettribCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("여행 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("trib", service.getTribList(requestDto));
    model.addAttribute("randomlist", service.RandomTribList());
    log.info("trib {}", service.getTribList(requestDto));
  }

  // 기타
  @GetMapping("/etc-category")
  public void getetcCategory(
    @ModelAttribute("requestDto") CategoryPageRequestDto requestDto,
    Model model
  ) {
    log.info("기타 목록 페이지 요청");
    log.info(service.getList(requestDto));

    model.addAttribute("etc", service.getEtcList(requestDto));
    model.addAttribute("randomlist", service.RandomEtcList());
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

    model.addAttribute("relationDto", productDtos);
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

  // 판매 내역
  @GetMapping("/sale_list")
  public void getSaleList(@RequestParam Long mid, Model model) {
    log.info("{} 멤버 판매 내역 페이지 요청", mid);

    List<Long> pnos = detailService.getPnos(mid);
    // log.info("판매내역 product : {}", productDtos);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();

    for (Long pno : pnos) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
    }
    log.info("판매내역 bidding : {}", biddingDtos);

    model.addAttribute("sale_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
  }

  // 위시리스트
  @GetMapping("/wish_list")
  public void getWishList(@RequestParam Long mid, Model model) {
    log.info("{} 멤버 찜 목록 페이지 요청", mid);

    List<Long> pnos = wishService.getPnos(mid);
    log.info("pnos : {}", pnos);

    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();

    for (Long pno : pnos) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
    }

    log.info("pDtos : {}", productDtos);

    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
  }

  @GetMapping("/comment_list")
  public void getCommentList(@RequestParam Long mid, Model model) {
    log.info("{} 멤버 댓글 목록 페이지 요청", mid);
    List<Long> comments = commentService.getCommentPno(mid);

    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<CommentDto> commentDtos = new ArrayList<CommentDto>();

    for (Long pno : comments) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      // commentDtos.addAll(commentService.getMemberComment(pno, mid));
      commentDtos.addAll(commentService.getCommentText(mid));
    }

    log.info("pDtos : {}", productDtos);
    log.info("cDtos : {}", commentDtos);

    model.addAttribute("product_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("comment_list", commentDtos);
  }

  // 고객센터
  @GetMapping("/customer-service")
  public void customerService() {
    log.info("고객센터 요청");
  }

  @GetMapping("/bidding_list")
  public void getbiddingList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPno(mid);
    // List<Long> bid_price = biddingService.getMyBiddingPrice(mid);
    log.info("biddings : {}", biddings);
    // log.info("bid_price : {}", bid_price);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPrice(mid));
    }
    log.info("pDtos : {}", productDtos);

    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
    // model.addAttribute(
    // "bid_price",
    // service.MemberBiddingList(principal.getName())
    // );
  }

  @GetMapping("/bidding/all")
  public void getbiddingdList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPno(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPrice(mid));
    }
    log.info("pDtos : {}", productDtos);

    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/fashion")
  public void getbiddingfashionList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/mobile")
  public void getbiddingmobileList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno2(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno2(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/electric")
  public void getbiddingelectricList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno3(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno3(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/game")
  public void getbiddinggameList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno4(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno4(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/trib")
  public void getbiddingtribList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno5(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno5(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }

  @GetMapping("/bidding/etc")
  public void getbiddingetcList(
    Model model,
    @RequestParam(value = "mid") Long mid
  ) {
    log.info("{} 멤버 응찰 목록 페이지 요청", mid);
    List<Long> biddings = biddingService.getBiddingPnoCno6(mid);
    log.info("biddings : {}", biddings);
    List<ProductDto> productDtos = new ArrayList<ProductDto>();
    List<BiddingDto> biddingDtos = new ArrayList<BiddingDto>();
    List<BiddingDto> biddingDtos2 = new ArrayList<BiddingDto>();

    for (Long pno : biddings) {
      productDtos.add(detailService.getRow(pno));
      biddingDtos.add(biddingService.getBestBidding(pno));
      biddingDtos2.addAll(biddingService.getMybidPriceCno6(mid));
    }
    log.info("pDtos : {}", productDtos);
    List<Payment> payments = new ArrayList<>();
    for (BiddingDto biddingDto : biddingDtos) {
      Payment payment = paymentRepository.findByBno(biddingDto.getBno());
      payments.add(payment);
    }

    // 회원 서비스를 통해 회원 정보 가져오기
    Optional<Member> optionalMember = memberRepository.findById(mid);
    Member member = optionalMember.get();

    model.addAttribute("payments", payments);
    model.addAttribute("member", member);
    model.addAttribute("wish_list", productDtos);
    model.addAttribute("bid_list", biddingDtos);
    model.addAttribute("bid_price", biddingDtos2);
  }
}
