package com.example.auctionarena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.auctionarena.entity.Wish;

public interface WishRepository extends JpaRepository<Wish, Long> {

    // 리스트 말고 멤버 번호까지 같이 가져올 수 없을까
    // @Query(value = "SELECT * FROM WISH w where w.product_pno = ?1", nativeQuery = true)
    List<Wish> findByProductPno(Long pno);
}
