package com.example.auctionarena.controller;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.service.BiddingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/biddings")
public class BiddingController {

  private final BiddingService biddingService;

  // 제품 입찰 기록 get
  @GetMapping("/{pno}/all")
  public ResponseEntity<List<BiddingDto>> getBiddings(
    @PathVariable("pno") Long pno,
    Model model
  ) {
    log.info("{}번 제품 응찰 리스트 출력 : ", pno);
    // model.addAttribute("biddingDto", biddingService.getBestBidding(pno));
    return new ResponseEntity<>(
      biddingService.getBiddingList(pno),
      HttpStatus.OK
    );
  }

  // 제품 입찰 등록 post
  @PostMapping("/{pno}")
  // BiddingDto biddingDto, @ModelAttribute("requestDto") PageRequestDto
  // pageRequestDto, RedirectAttributes rttr
  public ResponseEntity<Long> postBidding(@RequestBody BiddingDto biddingDto) {
    log.info("제품 응찰 요청 {}", biddingDto);

    Long bno = biddingService.bidRegister(biddingDto);
    return new ResponseEntity<Long>(bno, HttpStatus.OK);
  }
}
