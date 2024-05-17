package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private BiddingRepository biddingRepository;

    @Test
    public void productInsertTest() {

        // 제품 데이터 추가 / 제품 이미지 추가

        IntStream.rangeClosed(1, 50).forEach(i -> {
            Long cno = (long) (Math.random() * 6) + 1;
            Category category = Category.builder().cno(cno).build();

            Long mid = (long) (Math.random() * 99) + 1;
            Member member = Member.builder().mid(mid).build();

            Product product = Product.builder()
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
                ProductImage pImage = ProductImage.builder()
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
        IntStream.rangeClosed(1, 50).forEach(i -> {

            Long pno = (long) (Math.random() * 50) + 2;
            Product product = Product.builder().pno(pno).build();

            Long mid = (long) (Math.random() * 99) + 1;
            Member member = Member.builder().mid(mid).build();

            Bidding bidding = Bidding.builder()
                    .biddingPrice(i * 10000L)
                    .product(product)
                    .member(member)
                    .build();
            biddingRepository.save(bidding);
        });
    }

}
