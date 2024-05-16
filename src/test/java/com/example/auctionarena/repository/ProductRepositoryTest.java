package com.example.auctionarena.repository;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;

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

    // 완성 X
    @Test
    public void productInsertTest() {
        // 제품 데이터 추가 / 제품 이미지 추가
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Product product = Product.builder()
                    .title("제품" + i)
                    .content("개쩌는 상품..!" + i)
                    .startPrice(i * 1000L)
                    .biddingDate((i % 7L))
                    .member(Member.builder().mid(i % 5L).build())
                    .category(Category.builder().cno(i % 5L).build())
                    .build();
            productRepository.save(product);

            // int count = (int) (Math.random() * 10) + 1;

            // for (int j = 0; j < count; j++) {
            // ProductImage pImage = ProductImage.builder()
            // .uuid(UUID.randomUUID().toString())
            // .product(product)
            // .imgName("img" + j + ".jpg")
            // .build();
            // productImageRepository.save(pImage);
            // }
        });
    }

}
