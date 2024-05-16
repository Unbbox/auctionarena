package com.example.auctionarena.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.auctionarena.entity.Category;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void categoryInsertTest() {
        categoryRepository.save(Category.builder().categoryName("패션의류/잡화").build());
        categoryRepository.save(Category.builder().categoryName("모바일/태블릿").build());
        categoryRepository.save(Category.builder().categoryName("가전제품").build());
        categoryRepository.save(Category.builder().categoryName("게임").build());
        categoryRepository.save(Category.builder().categoryName("레저/여행").build());
        categoryRepository.save(Category.builder().categoryName("기타").build());
    }
}
