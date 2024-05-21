package com.example.auctionarena.service;

import java.util.List;

import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;

public interface CommentService {

    // 제품 판매글의 모든 리뷰 가져오기
    List<CommentDto> getCommentList(Long pno);

    //
    // CommentDto getComment(Long commentNo);

    public default CommentDto entityToDto(Comment comment) {
        return CommentDto.builder()
                .commentNo(comment.getCommentNo())
                .text(comment.getText())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .mid(comment.getMember().getMid())
                .nickname(comment.getMember().getNickname())
                .pno(comment.getProduct().getPno())
                .build();
    }

    public default Comment dtoToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentNo(commentDto.getCommentNo());
        comment.setText(commentDto.getText());
        comment.setMember(Member.builder().mid(commentDto.getMid()).build());
        comment.setCreatedDate(commentDto.getCreatedDate());
        comment.setProduct(Product.builder().pno(commentDto.getPno()).build());
        return comment;
    }
}
