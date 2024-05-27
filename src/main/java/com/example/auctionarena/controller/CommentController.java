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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService service;

    // 제품 상세페이지의 모든 댓글
    @GetMapping("/{pno}/all")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("pno") Long pno) {
        log.info("{}번 제품 댓글 가져오기", pno);
        return new ResponseEntity<>(service.getCommentList(pno), HttpStatus.OK);
    }

    // 댓글 등록
    @PostMapping("/{pno}")
    public ResponseEntity<Long> postComments(@RequestBody CommentDto commentDto) {
        log.info("리뷰 등록 : {}", commentDto);

        Long commentNo = service.insertComment(commentDto);
        return new ResponseEntity<Long>(commentNo, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{pno}/{commentNo}")
    public ResponseEntity<Long> putComments(@PathVariable("commentNo") Long commentNo,
            @RequestBody CommentDto commentDto) {
        log.info("comment 수정 {}", commentDto);

        service.updateComment(commentDto);

        return new ResponseEntity<>(commentNo, HttpStatus.OK);
    }
}
