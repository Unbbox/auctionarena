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
    value = "SELECT distinct b.product_pno FROM BIDDING b WHERE b.MEMBER_MID = ?1 ORDER BY b.PRODUCT_PNO desc",
    nativeQuery = true
  )
  List<Long> findBybiddingPno(Long mid);

  // @Query(
  //   value = "SELECT max(b.BIDDING_PRICE),b.PRODUCT_PNO FROM BIDDING b WHERE b.MEMBER_MID = ?1 GROUP BY b.PRODUCT_PNO",
  //   nativeQuery = true
  // )
  // Bidding findBymybiddingPrice(Long mid);
  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING WHERE member_mid = ?1 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPrice(Long mid);
}
