package com.example.auctionarena.service;

import com.example.auctionarena.dto.CommentDto;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository repository;

  @Override
  public List<CommentDto> getCommentList(Long pno) {
    log.info("판매 제품의 댓글 가져오기 {}", pno);

    Product product = Product.builder().pno(pno).build();
    List<Comment> comments = repository.findByProductOrderByCreatedDateDesc(
      product
    );
    log.info("comments : ", comments);

    // entity to dto
    Function<Comment, CommentDto> fn = comment -> entityToDto(comment);
    return comments.stream().map(fn).collect(Collectors.toList());
  }

  @Override
  public Long insertComment(CommentDto commentDto) {
    log.info("{}번 제품 댓글 등록", commentDto.getPno());
    Comment comment = dtoToEntity(commentDto);

    return repository.save(comment).getCommentNo();
  }

  @Override
  public void removeComment(Long commentNo) {
    log.info("{}번 댓글 삭제", commentNo);
    repository.deleteById(commentNo);
  }

  @Override
  public List<Long> getCommentPno(Long mid) {
    List<Long> comments = repository.findCommentPno(mid);
    log.info("commentsService comments : {}", comments);
    return comments;
  }

  @Override
  public CommentDto getMemberComment(Long pno) {
    Product product = Product.builder().pno(pno).build();
    // Member member = Member.builder().mid(mid).build();

    Comment comments = repository.findByMember(product);

    return entityToDto(comments);
  }

  @Override
  public List<CommentDto> getCommentText(Long mid) {
    List<Comment> comment = repository.findCommentText(mid);
    Function<Comment, CommentDto> fn = comments -> entityToDto(comments);
    return comment.stream().map(fn).collect(Collectors.toList());
  }
}
