package com.example.auctionarena.repository;

import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
  // @Query(
  // value = "SELECT * FROM PRODUCT p LEFT JOIN" +
  // " (SELECT pi2.product_pno,pi2.PATH, pi2.uuid, ROW_NUMBER() OVER (ORDER BY
  // pi2.product_pno desc) AS RankNo " +
  // " FROM PRODUCT_IMAGE pi2 " +
  // " WHERE pi2.inum IN (SELECT min(inum) FROM PRODUCT_IMAGE GROUP BY
  // product_pno))pi3" +
  // " ON p.pno = pi3.product_pno" +
  // " WHERE RankNo < 7 ORDER BY RankNo ASC",
  // nativeQuery = true
  // )
  // List<Object[]> findTop6ByOrderByPnoDesc();
  List<Product> findTop6ByOrderByPnoDesc();

  @Query(value = "SELECT * FROM PRODUCT p LEFT JOIN " +
      " (SELECT b.product_pno,count(b.PRODUCT_PNO) AS cnt, ROW_NUMBER() OVER (ORDER BY count(b.PRODUCT_PNO) DESC) AS RankNo "
      +
      " FROM BIDDING b GROUP BY b.PRODUCT_PNO)b1 oN p.pno = b1.product_pno " +
      " WHERE b1.product_pno IS NOT NULL AND RankNo < 7 ORDER BY Rankno asc", nativeQuery = true)
  List<Product> findTop6ByOrderByBiddingCntDesc();

  // 관련 카테고리
  @Query(value = "SELECT * " +
      "FROM (SELECT * " +
      "FROM PRODUCT p " +
      "WHERE p.CATEGORY_CNO = (SELECT CATEGORY_CNO " +
      "FROM product p " +
      "WHERE PNO = ?1) " +
      "AND NOT p.pno = ?1 " +
      "ORDER BY p.CREATED_DATE DESC)" +
      "WHERE rownum <= 10", nativeQuery = true)
  List<Product> findByProductList(Long pno);

  // 카테고리 선택별 제품 출력용
  @Query(value = " SELECT * FROM PRODUCT p WHERE p.CATEGORY_CNO = 1 ORDER BY CREATED_DATE DESC ", nativeQuery = true)
  List<Product> getCategoryByCno(Long cno);
  @Query(
    value = "SELECT DISTINCT p.* from BIDDING b LEFT JOIN product p " +
    " ON p.PNO = b.PRODUCT_PNO WHERE b.MEMBER_MID = ?1",
    nativeQuery = true
  )
  List<Product> findbiddingList(Long mid);
}
