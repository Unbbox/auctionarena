package com.example.auctionarena.controller;

import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.service.WishService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/wish")
public class WishController {

  private final WishService service;

  @GetMapping("/{pno}/{mid}")
  public ResponseEntity<WishDto> getWish(
    @PathVariable("pno") Long pno,
    @PathVariable("mid") Long mid,
    Model model
  ) {
    log.info("찜 목록 보여주기 {}", pno);
    log.info("멤버 아이디 정보 {}", mid);

    return new ResponseEntity<>(service.getRow(pno, mid), HttpStatus.OK);
  }

  @PostMapping("/add/{pno}")
  // public ResponseEntity<Long> postWish(@RequestBody Long pno, @RequestBody Long
  // mid) {
  public ResponseEntity<Long> postAddWish(@RequestBody WishDto wishDto) {
    log.info("찜하기 요청 : {}", wishDto);

    Long wno = service.addWish(wishDto);

    return new ResponseEntity<Long>(wno, HttpStatus.OK);
  }

  @PostMapping("/remove/{pno}")
  // public ResponseEntity<Long> postWish(@RequestBody Long pno, @RequestBody Long
  // mid) {
  public ResponseEntity<Long> postRemoveWish(@RequestBody WishDto wishDto) {
    log.info("찜제거 요청 : {}", wishDto);

    Long wno = service.removeWish(wishDto);

    return new ResponseEntity<Long>(wno, HttpStatus.OK);
  }
}
