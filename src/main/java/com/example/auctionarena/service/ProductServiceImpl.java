package com.example.auctionarena.service;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.ProductRepository;
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

  @Override
  public List<ProductDto> getList() {
    List<Product> list = productRepository.findAll();
    List<ProductDto> productList = list
      .stream()
      .map(product -> entityToDto(product, null))
      .collect(Collectors.toList());

    return productList;
  }
}
