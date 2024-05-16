package com.example.auctionarena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Modifying
    @Query("delete from ProductImage pi where pi.product = :product")
    void deleteByMovie(Product product);

    // @Query(value = "SELECT * FROM PRODUCT_IMAGE pi WHERE pi.PATH =
    // TO_CHAR(SYSDATE - 1, 'yyyy\\mm\\dd')", nativeQuery = true)
    // List<ProductImage> getOldProductImages();
}
