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
  @Query(
    value = "SELECT * FROM PRODUCT p WHERE rownum <= 6 ORDER BY pno desc",
    nativeQuery = true
  )
  List<Product> findTop6ByOrderByPnoDesc();
}
