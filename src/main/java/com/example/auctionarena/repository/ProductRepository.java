package com.example.auctionarena.repository;

import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
  extends JpaRepository<Product, Long>, SearchProductRepository {
  // @Query(
  //   value = "SELECT * FROM PRODUCT p WHERE rownum <= 6 ORDER BY pno desc",
  //   nativeQuery = true
  // )
  List<Product> findTop6ByOrderByPnoDesc();

  @Query(
    value = "SELECT * FROM PRODUCT p LEFT JOIN " +
    " (SELECT b.product_pno,count(b.PRODUCT_PNO) AS cnt, ROW_NUMBER() OVER (ORDER BY count(b.PRODUCT_PNO) DESC) AS RankNo FROM BIDDING b GROUP BY b.PRODUCT_PNO)b1 oN p.pno = b1.product_pno " +
    " WHERE b1.product_pno IS NOT NULL AND RankNo < 7 ORDER BY Rankno asc",
    nativeQuery = true
  )
  List<Product> findTop6ByOrderByBiddingCntDesc();
}
