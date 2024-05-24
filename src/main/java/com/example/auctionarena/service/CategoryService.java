package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Product;

public interface CategoryService {
  CategoryDto getRow(Long id);

  // CategoryPageResultDto<CategoryDto, Object[]> getList(
  //   CategoryPageRequestDto requestDto
  // );

  public default CategoryDto entityToDto(Category category) {
    return CategoryDto
      .builder()
      .cno(category.getCno())
      .categoryName(category.getCategoryName())
      // .title(category.getProduct().getTitle())
      // .pno(category.getProduct().getPno())
      // .startPrice(category.getProduct().getStartPrice())
      // .createdDate(category.getProduct().getCreatedDate())
      // .lastModifiedDate(category.getProduct().getLastModifiedDate())
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
