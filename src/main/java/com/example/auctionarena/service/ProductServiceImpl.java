package com.example.auctionarena.service;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.ProductImageRepository;
import com.example.auctionarena.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
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
  private final ProductImageRepository productImageRepository;

  @Override
  public List<ProductDto> getList() {
    List<Product> list = productRepository.findAll();
    List<ProductDto> productList = list
      .stream()
      .map(product -> entityToDto(product, null))
      .collect(Collectors.toList());

    return productList;
  }

  // 제품 상세 페이지
  @Override
  public ProductDto getRow(Long pno) {
    Product entity = productRepository.findById(pno).get();

    return entityToDto(entity, null);
    /*
     * // 나중에 도전
     */
    // List<Object[]> result = productImageRepository.getProductRow(pno);
    // Product product = (Product) result.get(0)[0];

    // // result 길이만큼 반복
    // List<ProductImage> productImages = new ArrayList<>();
    // result.forEach(arr -> {
    // ProductImage productImage = (ProductImage) arr[1];
    // productImage.add(productImage);
    // });

    // Long reviewCnt = (Long) result.get(0)[2];

    // return entityToDto(product, productImages, reviewCnt);
  }
}
