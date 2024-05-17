package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
