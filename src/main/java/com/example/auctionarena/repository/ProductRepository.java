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
  Product findByPno(Long pno);

  List<Product> findTop6ByOrderByPnoDesc();

  @Query(
    value = "SELECT * FROM PRODUCT p LEFT JOIN " +
    " (SELECT b.product_pno,count(b.PRODUCT_PNO) AS cnt, ROW_NUMBER() OVER (ORDER BY count(b.PRODUCT_PNO) DESC) AS RankNo " +
    " FROM BIDDING b GROUP BY b.PRODUCT_PNO)b1 oN p.pno = b1.product_pno " +
    " WHERE b1.product_pno IS NOT NULL AND RankNo < 7 ORDER BY Rankno asc",
    nativeQuery = true
  )
  List<Product> findTop6ByOrderByBiddingCntDesc();

  // 관련 카테고리
  @Query(
    value = "SELECT * " +
    "FROM (SELECT * " +
    "FROM PRODUCT p " +
    "WHERE p.CATEGORY_CNO = (SELECT CATEGORY_CNO " +
    "FROM product p " +
    "WHERE PNO = ?1) " +
    "AND NOT p.pno = ?1 " +
    "ORDER BY p.CREATED_DATE DESC)" +
    "WHERE rownum <= 10",
    nativeQuery = true
  )
  List<Product> findByProductList(Long pno);

  @Query(
    value = "SELECT DISTINCT p.* from BIDDING b LEFT JOIN product p " +
    " ON p.PNO = b.PRODUCT_PNO WHERE b.MEMBER_MID = ?1",
    nativeQuery = true
  )
  List<Product> findbiddingList(Long mid);

  // 판매 내역 리스트
  List<Product> findByMemberOrderByCreatedDateDesc(Member member);

  @Query(
    value = "SELECT pno " +
    "FROM PRODUCT p " +
    "WHERE MEMBER_MID = ?1 " +
    "ORDER by created_date DESC",
    nativeQuery = true
  )
  List<Long> findByMemberMid(Long mid);

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 1 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findfashionRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 2 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findmobileRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 3 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findelectricRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 4 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findgameRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 5 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findtribRandomList();

  @Query(
    value = "SELECT p1.*FROM (SELECT p.* FROM product p  WHERE p.category_cno = 6 ORDER BY DBMS_RANDOM.VALUE)p1 WHERE ROWNUM <= 6",
    nativeQuery = true
  )
  List<Product> findetcRandomList();
}
