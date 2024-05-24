package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 분류명으로 찾기
    Optional<Category> findByCategoryName(String name);
}
