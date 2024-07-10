package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
  // 추후 응찰 내역 비교 시 사용(최고가 가져와서 출력)
  @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
  List<Bidding> findByProductOrderByCreatedDateDesc(Product product);

  // 최고가 출력
  Bidding findTop1ByProductOrderByBiddingPriceDesc(Product product);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b WHERE b.MEMBER_MID = ?1",
    nativeQuery = true
  )
  List<Long> findBybiddingPno(Long mid);

  // @Query(
  //   value = "SELECT max(b.BIDDING_PRICE),b.PRODUCT_PNO FROM BIDDING b WHERE b.MEMBER_MID = ?1 GROUP BY b.PRODUCT_PNO",
  //   nativeQuery = true
  // )
  // Bidding findBymybiddingPrice(Long mid);
  @Query(
    value = "SELECT DISTINCT max(b.BIDDING_PRICE) OVER (PARTITION BY product_pno ORDER BY bidding_price DESC) AS max FROM BIDDING b WHERE b.MEMBER_MID = ?1",
    nativeQuery = true
  )
  Bidding findBymybiddingPrice(Long mid);
}
