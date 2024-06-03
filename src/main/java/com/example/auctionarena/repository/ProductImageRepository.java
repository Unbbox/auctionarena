package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.productDetail.ProductImageCommentRepository;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository
  extends
    JpaRepository<ProductImage, Long>,
    ProductImageCommentRepository,
    SearchProductRepository {
  // 제품 상세 페이지 삭제 시
  @Modifying
  @Query("delete from ProductImage pi where pi.product = :product")
  void deleteByProduct(Product product);

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
}
