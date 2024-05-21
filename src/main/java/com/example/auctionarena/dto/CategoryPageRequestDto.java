package com.example.auctionarena.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
public class CategoryPageRequestDto {

  private int page;
  private int size;

  // 검색하면서 따라 들어가기 위해 늘어남
  // 초기화 안 하면 null
  private String type;
  private String keyword;
  private String category;

  // 원래 들어오지만 안 들어올 경우 기본값 페이지1, 사이즈 10
  // localhost8080 에서도 들어가기 위해 type, keyword 기본값 줌 (Nullpointerexception 방지)
  public CategoryPageRequestDto() {
    this.page = 1;
    this.size = 24;
    this.type = "";
    this.keyword = "";
    this.category = "";
  }

  // 스프링 페이지 나누기 정보 저장 → Pageable
  // 1 page → 0 부터 시작 (0 : 1페이지, 1 : 2페이지 ~) → page - 1
  public Pageable getPageable(Sort sort) {
    return PageRequest.of(page - 1, size, sort);
  }
}
