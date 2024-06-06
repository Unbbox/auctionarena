package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.productDetail.ProductImageCommentRepository;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository
  extends
    JpaRepository<ProductImage, Long>,
    ProductImageCommentRepository,
    SearchProductRepository {

  // 제품 상세 페이지 삭제 시
  @Transactional
  @Modifying
  // @Query("delete from ProductImage pi where pi.product = :product")
  @Query(value = "DELETE FROM PRODUCT_IMAGE pi WHERE pi.PRODUCT_PNO = ?1",
  nativeQuery = true)
  void deleteByProduct(Long pno);

  // 지난 게시글의 이미지 가져오기
  @Query(
    value = "SELECT * FROM PRODUCT_IMAGE pi WHERE pi.PATH = TO_CHAR(SYSDATE - 1, 'yyyy\\mm\\dd')",
    nativeQuery = true
  )
  List<ProductImage> getOldProductImages();

  @Query(
    value = "SELECT * " +
    "FROM PRODUCT_IMAGE pi2 " +
    "WHERE pi2.PRODUCT_PNO in (SELECT pno " +
    "FROM (SELECT * " +
    "FROM PRODUCT p  " +
    "WHERE p.CATEGORY_CNO = (SELECT CATEGORY_CNO " +
    "FROM product p " +
    "WHERE PNO = ?1) " +
    "AND NOT p.pno = ?1 " +
    "ORDER BY p.CREATED_DATE DESC) " +
    "WHERE rownum <= 5)",
    nativeQuery = true
  )
  List<ProductImage> getRelationRow(Long pno);

  @Query(
    value = "SELECT * FROM PRODUCT_IMAGE pi2 LEFT JOIN" +
    " (SELECT p.pno,p.title, p.start_price, ROW_NUMBER() OVER (ORDER BY p.pno desc) AS RankNo " +
    " FROM PRODUCT p)pi3 " +
    " ON pi2.product_pno = pi3.pno" +
    " WHERE RankNo <= 6 AND pi2.inum IN (SELECT min(inum) FROM PRODUCT_IMAGE GROUP BY product_pno) ORDER BY RankNo ASC",
    nativeQuery = true
  )
  List<ProductImage> orderByPnoDesc();

  @Query(
    value = "SELECT pi2.INUM,pi2.IMG_NAME,pi2.UUID,pi2.PRODUCT_PNO,pi2.PATH  " +
    " FROM PRODUCT_IMAGE pi2 LEFT JOIN " +
    " (SELECT * FROM PRODUCT p)pi3 ON pi2.PRODUCT_PNO = pi3.pno LEFT JOIN " +
    " (SELECT b.product_pno,count(b.PRODUCT_PNO)AS cnt, ROW_NUMBER() OVER (ORDER BY count(b.PRODUCT_PNO) DESC) AS RankNob FROM BIDDING b GROUP BY b.PRODUCT_PNO)b1 " +
    " ON pi3.pno = b1.product_pno WHERE b1.product_pno IS NOT NULL AND RankNob < 7 AND pi2.inum IN (SELECT min(inum) " +
    " FROM PRODUCT_IMAGE GROUP BY product_pno) ORDER BY Ranknob ASC",
    nativeQuery = true
  )
  List<ProductImage> orderByBiddingDesc();
}
