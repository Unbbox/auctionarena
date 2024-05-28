package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductServiceTest {

  @Autowired
  private ProductService service;

  //   @Test
  //   public void serviceList() {
  //     System.out.println(service.getList());
  //   }

  // @Transactional
  // @Test
  // public void getRow() {
  //   Object[] row = productRepository.
  // }
  @Transactional
  @Test
  public void serviceRead() {
    System.out.println(service.descList());
  }
}
