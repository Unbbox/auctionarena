package com.example.auctionarena.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService service;

    // 제품 상세페이지의 모든 댓글
    @GetMapping("/product/{pno}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("pno") Long pno) {
        log.info("{}번 제품 댓글 가져오기", pno);
        return new ResponseEntity<>(service.getCommentList(pno), HttpStatus.OK);
    }

}
