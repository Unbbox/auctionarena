package com.example.auctionarena.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public List<CommentDto> getCommentList(Long pno) {
        log.info("판매 제품의 댓글 가져오기 {}", pno);

        Product product = Product.builder().pno(pno).build();
        List<Comment> comments = repository.findByProduct(product);

        // board의 댓글 코드
        // List<Comment> comments = repository.getCommentsByProductOrderByCno(product);

        // entity to dto
        Function<Comment, CommentDto> fn = comment -> entityToDto(comment);
        return comments.stream().map(fn).collect(Collectors.toList());
    }

}
