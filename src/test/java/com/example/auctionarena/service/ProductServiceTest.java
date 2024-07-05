package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.repository.NoticeRepository;
import com.example.auctionarena.repository.ProductRepository;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductServiceTest {

  @Autowired
  private ProductService service;

  @Autowired
  private DetailService detailService;

  @Autowired
  private SearchProductRepository searchProductRepository;

  // @Autowired
  // private ProductImageService service;

  @Transactional
  @Test
  public void serviceList() {
    List<ProductDto> list = service.pnodescList();

    System.out.println("테스트");

    list.forEach(product -> {
      System.out.println(product);
    });
  }

  // @Transactional
  // @Test
  // public void getRow() {
  // Object[] row = productRepository.
  // }
  // @Transactional
  // @Test
  // public void serviceRead() {
  // System.out.println(service.descList());
  // }

  @Transactional
  @Test
  public void printCate() {
    List<ProductDto> products = detailService.getRelationList(1L);

    products.forEach(product -> {
      System.out.println(product);
    });
  }

  @Transactional
  @Test
  public void printCategory() {
    List<ProductDto> products = service.CategoryList(1L);

    products.forEach(product -> {
      System.out.println(product);
    });
  }
}
