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

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 1 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno(Long mid);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 2 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno2(Long mid);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 3 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno3(Long mid);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 4 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno4(Long mid);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 5 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno5(Long mid);

  @Query(
    value = "SELECT distinct b.product_pno FROM BIDDING b LEFT JOIN PRODUCT p ON b.PRODUCT_PNO = p.PNO " +
    " WHERE b.MEMBER_MID = ?1 AND p.CATEGORY_CNO = 6 " +
    " ORDER BY b.PRODUCT_PNO DESC",
    nativeQuery = true
  )
  List<Long> findBybiddingPnoCno6(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING WHERE member_mid = ?1 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPrice(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 1 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 2 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno2(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 3 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno3(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 4 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno4(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 5 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno5(Long mid);

  @Query(
    value = "SELECT b.* FROM bidding b INNER JOIN " +
    " (SELECT product_pno,max(bidding_price) AS max_bidding_price FROM BIDDING b1 LEFT JOIN PRODUCT p " +
    " ON b1.product_pno = p.pno WHERE b1.member_mid = ?1 AND p.category_cno = 6 GROUP BY product_pno)b2 " +
    " ON b.product_pno = b2.product_pno AND b.bidding_price = b2.max_bidding_price " +
    " ORDER BY b2.product_pno desc",
    nativeQuery = true
  )
  List<Bidding> findBymybiddingPriceCno6(Long mid);
}
