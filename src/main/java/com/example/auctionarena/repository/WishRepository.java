package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.Wish;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishRepository extends JpaRepository<Wish, Long> {
  // 리스트 말고 멤버 번호까지 같이 가져올 수 없을까
  // @Query(value = "SELECT * FROM WISH w where w.product_pno = ?1", nativeQuery =
  // true)
  // WishDto findByProductPno(Long pno);
  // List<WishDto> findByProductPno(Long pno);

  // @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
  // List<Wish> findByProduct(Product product);

  // @EntityGraph(attributePaths = { "product" }, type = EntityGraphType.FETCH)
  // List<Wish> findByMember(Member member);

  @Query(value = "SELECT COUNT(*) FROM WISH w where w.member_mid = ?1", nativeQuery = true)
  Long findByMemberMidCnt(Long mid);

  Wish findByProductAndMember(
      @Param("product") Product product,
      @Param("member") Member member);

  @Query(value = "SELECT w.product_pno FROM WISH w where w.member_mid = ?1", nativeQuery = true)
  List<Long> findByMemberMid(Long mid);
}
