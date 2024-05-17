package com.example.auctionarena.repository.productSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepository {
  Page<Object[]> list(String type, String keyword, Pageable pageable);
}