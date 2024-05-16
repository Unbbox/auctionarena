package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.entity.Category;

public interface CategoryService {
  public default CategoryDto entityToDto(Category category) {
    return CategoryDto
      .builder()
      .cno(category.getCno())
      .categoryName(category.getCategoryName())
      .build();
  }

  public default Category dtoToEntity(CategoryDto dto) {
    return Category
      .builder()
      .cno(dto.getCno())
      .categoryName(dto.getCategoryName())
      .build();
  }
}
