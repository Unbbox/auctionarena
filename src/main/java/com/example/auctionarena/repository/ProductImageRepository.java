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
}
