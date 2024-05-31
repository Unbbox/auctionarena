package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.CategoryRepository;
import com.example.auctionarena.repository.ProductImageRepository;
import com.example.auctionarena.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductImageRepository productImageRepository;

  // @Override
  // public List<ProductDto> getList() {
  // List<Product> list = productRepository.findAll();
  // List<ProductDto> productList = list
  // .stream()
  // .map(product -> entityToDto(product, null))
  // .collect(Collectors.toList());

  // return productList;
  // }
  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.list(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
    // (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
  }
  // @Override
  // public List<ProductDto> descList() {
  //   List<Product> list = productRepository.findTop6ByOrderByPnoDesc();
  //   return list
  //     .stream()
  //     .map(entity -> entityToDto(entity))
  //     .collect(Collectors.toList());
  // }

  // @Override
  // public List<ProductDto> descList() {
  //   List<Product> list = productRepository.findTop6ByOrderByPnoDesc();
  //   return list
  //     .stream()
  //     .map(entity -> entityToDto(entity))
  //     .collect(Collectors.toList());
  // }

  // @Override
  // public List<ProductDto> descList() {
  //   List<Product> list = productRepository.findTop6ByOrderByPnoDesc();
  //   return list
  //     .stream()
  //     .map(entity -> entityToDto(entity))
  //     .collect(Collectors.toList());
  // }

  // @Override
  // public List<ProductDto> BiddingDescList() {
  //   List<Product> list = productRepository.findTop6ByOrderByBiddingCntDesc();
  //   return list
  //     .stream()
  //     .map(entity -> entityToDto(entity))
  //     .collect(Collectors.toList());
  // }
}
