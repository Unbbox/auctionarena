package com.example.auctionarena.repository;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
// import com.example.auctionarena.repository.biddingDetail.BiddingDetailRepository;
import com.example.auctionarena.entity.Comment;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.productSearch.SearchProductRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private BiddingRepository biddingRepository;

  @Autowired
  private CommentRepository commentRepository;

  // 문제 투성이
  @Autowired
  private SearchProductRepository searchProductRepository;

  @Autowired
  private ProductImageRepository productImageRepository;
  //

  @Transactional
  @Test
  public void productInsertTest() {
    IntStream
        .rangeClosed(1, 300)
        .forEach(i -> {
          Long cno = (long) (Math.random() * 6) + 1;
          Category category = Category.builder().cno(cno).build();

          Long mid = (long) (Math.random() * 40) + 1;
          Member member = Member.builder().mid(mid).build();

          Product product = Product
              .builder()
              .title("제품" + i)
              .content("개쩌는 상품..!" + i)
              .startPrice(i * 1000L)
              .biddingDate((i % 7L) + 1)
              .member(member)
              .category(category)
              .build();
          productRepository.save(product);

          int count = (int) (Math.random() * 10) + 1;

          for (int j = 0; j < count; j++) {
            ProductImage pImage = ProductImage
                .builder()
                .uuid(UUID.randomUUID().toString())
                .product(product)
                .imgName("img" + j + ".jpg")
                .build();
            productImageRepository.save(pImage);
          }
        });
  }

  @Test
  public void biddingDataInsertTest() {
    // 응찰 관련 데이터 삽입
    IntStream
        .rangeClosed(1, 50)
        .forEach(i -> {
          Long pno = (long) (Math.random() * 50) + 1;
          Product product = Product.builder().pno(pno).build();

          Long mid = (long) (Math.random() * 99) + 1;
          Member member = Member.builder().mid(mid).build();

          Bidding bidding = Bidding
              .builder()
              .biddingPrice(i * 10000L)
              .product(product)
              .member(member)
              .build();
          biddingRepository.save(bidding);
        });
  }

  @Test
  public void commentSampleTest() {
    IntStream
        .rangeClosed(1, 300)
        .forEach(i -> {
          Long pno = (long) (Math.random() * 50) + 1;
          Product product = Product.builder().pno(pno).build();

          Long mid = (long) (Math.random() * 99) + 1;
          Member member = Member.builder().mid(mid).build();

          Comment comment = Comment
              .builder()
              .text("이 제품 진짜 좋은건가요?" + i)
              .product(product)
              .member(member)
              .build();

          commentRepository.save(comment);
        });
  }

  @Commit
  @Transactional
  @Test
  public void removeProduct() {
    Product product = Product.builder().pno(300L).build();

    productRepository.delete(product);
  }

  @Test
  public void noiceImageList() {
    PageRequestDto requestDto = PageRequestDto
        .builder()
        .page(1)
        .size(24)
        .build();

    Page<Object[]> list = searchProductRepository.mobilecatelist(
        requestDto.getType(),
        requestDto.getKeyword(),
        requestDto.getPageable(Sort.by("pno").descending()));

    for (Object[] objects : list) {
      System.out.println(Arrays.toString(objects));
    }
  }
}
