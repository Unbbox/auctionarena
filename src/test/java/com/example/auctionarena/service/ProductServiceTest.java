package com.example.auctionarena.service;

import com.example.auctionarena.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class ProductServiceTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService service;

  //   @Test
  //   public void serviceList() {
  //     System.out.println(service.getList());
  //   }
  @Transactional
  @Test
  public void movieListTest() {
    PageRequest pageRequest = PageRequest.of(
      0,
      10,
      Sort.by("pno").descending()
    );

    Page<Object[]> list = productRepository.list("t", "제품", pageRequest);

    for (Object[] objects : list) {
      System.out.println(Arrays.toString(objects));
    }
  }
}
