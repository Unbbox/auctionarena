package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  // 제품(게시글) 번호로 댓글 가져오기
  // @EntityGraph : DataJPA 에서 fetch 조인을 위한 설정
  @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
  List<Comment> findByProductOrderByCreatedDateDesc(Product product);

  // board의 댓글 코드
  // List<Comment> getCommentsByProductOrderByCno(Product product);

  @Query(
    value = "SELECT c.product_pno FROM COMMENTS c WHERE c.MEMBER_MID = ?1 ORDER BY c.PRODUCT_PNO desc",
    nativeQuery = true
  )
  List<Long> findCommentPno(Long mid);

  Comment findByMember(
    @Param("product") Product product
    // @Param("member") Member member
  );

  @Query(
    value = "SELECT c.* FROM Comments c WHERE c.member_mid = ?1 ORDER BY c.PRODUCT_PNO desc",
    nativeQuery = true
  )
  List<Comment> findCommentText(Long mid);
}
