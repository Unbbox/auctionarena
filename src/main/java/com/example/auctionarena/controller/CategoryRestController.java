package com.example.auctionarena.controller;

import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class CategoryRestController {

  private final CategoryService categoryService;

  @GetMapping("/category/{id}")
  public ResponseEntity<CategoryDto> read(@PathVariable Long id) {
    log.info("category 요청{}", id);
    CategoryDto categoryDto = categoryService.getRow(id);

    return new ResponseEntity<>(categoryDto, HttpStatus.OK);
  }
}
