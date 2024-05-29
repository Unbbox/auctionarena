package com.example.auctionarena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Product;

public interface BiddingRepository extends JpaRepository<Bidding, Long> {
    // 추후 응찰 내역 비교 시 사용(최고가 가져와서 출력)
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Bidding> findByProductOrderByCreatedDateDesc(Product product);
}