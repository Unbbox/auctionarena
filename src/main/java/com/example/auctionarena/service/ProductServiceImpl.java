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
    Page<Object[]> result = productRepository.list(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (entity -> entityToDto((Product) entity[0]));
    return new CategoryPageResultDto<>(result, fn);
  }
  // @Override
  // public ProductDto getRow(Long cno) {
  //   // Product entitiy = productRepository.findByCno(cno).get();
  //   // return entityToDto(entitiy);
  //   Object[] row = productRepository.findByCno(cno);
  //   return entityToDto((Product) row[0]);
  // }
  // 제품 상세 페이지
  // @Override
  // public ProductDto getRow(Long pno) {
  // Product entity = productRepository.findById(pno).get();

  // return entityToDto(entity, null, null);
  // }

  // @Override
  // public ProductDto getRow(Long pno) {
  // List<Object[]> result = productImageRepository.getProductRow(pno);

  // // result의 값 첫번째 == product
  // Product product = (Product) result.get(0)[0];

  // // result 길이만큼 반복
  // List<ProductImage> productImages = new ArrayList<>();
  // result.forEach(arr -> {
  // // productImage 개수 만큼 이미지 가져오기
  // ProductImage productImage = (ProductImage) arr[1];
  // productImages.add(productImage);
  // });

  // Long reviewCnt = (Long) result.get(0)[2];

  // return entityToDto(product, null, reviewCnt);
  // }
}
