package com.example.auctionarena.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auctionarena.dto.CategoryDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/category")
@RestController
public class CategoryPrintController {

    private final ProductRepository productRepository;

    @GetMapping("/cate/{cno}")
    public ResponseEntity<List<ProductDto>> getMethodName(@PathVariable("cno") Long cno) {
        log.info("특정 카테고리 출력 {}", cno);

        List<ProductDto> productDtos = productRepository.getCategoryByCno(cno);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
