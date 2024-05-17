package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
  extends JpaRepository<Product, Long>, SearchProductRepository {}
