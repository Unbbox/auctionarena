package com.example.auctionarena.repository.productSearch;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.ProductImage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepository {
  Page<Object[]> list(String type, String keyword, Pageable pageable);
}
