package com.example.auctionarena.service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import java.util.List;

public interface CommentService {
  // 제품 판매글의 모든 리뷰 가져오기
  List<CommentDto> getCommentList(Long pno);

  // 해당 판매글의 댓글 등록
  Long insertComment(CommentDto commentDto);

  // 댓글 삭제
  void removeComment(Long commentNo);

  List<Long> getCommentPno(Long pno);

  // 특정 제품의 특정 멤버가 작성한 코멘트 가져오기
  CommentDto getMemberComment(Long mid);

  List<CommentDto> getCommentText(Long mid);

  public default CommentDto entityToDto(Comment comment) {
    return CommentDto
      .builder()
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
