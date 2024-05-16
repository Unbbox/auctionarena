package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Product;
import java.util.Date;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void productInsertTest() {
    // 제품 데이터 추가
    IntStream
      .rangeClosed(1, 50)
      .forEach(i -> {
        Product product = Product
          .builder()
          .title("제품" + i)
          .content("개쩌는 상품..!" + i)
          .startPrice(i * 1000L)
          .biddingDate(Integer.toString(i % 7))
          .category(Category.builder().cno(i % 5L).build())
          .build();
        productRepository.save(product);
      });
  }
}
