package com.example.auctionarena.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.dto.WishDto;
import com.example.auctionarena.service.WishService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/wish")
public class WishController {

    private final WishService service;
    
    @PostMapping("/add/{pno}")
    // public ResponseEntity<Long> postWish(@RequestBody Long pno, @RequestBody Long mid) {
    public ResponseEntity<Long> postAddWish(@RequestBody WishDto wishDto) {
        log.info("찜하기 요청 : {}, {}", wishDto);

        Long wno = service.addWish(wishDto);

        return new ResponseEntity<Long>(wno, HttpStatus.OK);
    }
    
    @PostMapping("/remove/{pno}")
    // public ResponseEntity<Long> postWish(@RequestBody Long pno, @RequestBody Long mid) {
    public ResponseEntity<Long> postRemoveWish(@RequestBody WishDto wishDto) {
        log.info("찜제거 요청 : {}, {}", wishDto);

        Long wno = service.removeWish(wishDto);

        return new ResponseEntity<Long>(wno, HttpStatus.OK);
    }
}
