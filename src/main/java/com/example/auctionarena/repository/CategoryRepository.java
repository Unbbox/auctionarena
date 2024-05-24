package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
