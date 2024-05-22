package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
  extends JpaRepository<Product, Long>, SearchProductRepository {
  // Object[] findByCno(Long cno);
}
