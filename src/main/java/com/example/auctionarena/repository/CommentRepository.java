package com.example.auctionarena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Product;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 제품(게시글) 번호로 댓글 가져오기
    // @EntityGraph : DataJPA 에서 fetch 조인을 위한 설정
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Comment> findByProduct(Product product);

    // board의 댓글 코드
    // List<Comment> getCommentsByProductOrderByCno(Product product);
}
