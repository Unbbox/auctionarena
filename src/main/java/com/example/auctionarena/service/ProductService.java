package com.example.auctionarena.service;

import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Product;

public interface ProductService {

    // entity => dto
    // public default ProductDto entityToDto(Product product, Member member, Long
    // replyCount) {
    public default ProductDto entityToDto(Product product, Long replyCount) {

        return ProductDto.builder()
                .pno(product.getPno())
                .title(product.getTitle())
                .content(product.getContent())
                // .writerEmail(member.getEmail())
                // .writerName(member.getName())
                .replyCount(replyCount != null ? replyCount : 0)
                .createdDate(product.getCreatedDate())
                .lastModifiedDate(product.getLastModifiedDate())
                .build();
    }

    // dto => entity
    public default Product dtoToEntity(ProductDto dto) {

        // Member member = Member.builder().email(dto.getWriterName()).build();

        return Product.builder()
                .pno(dto.getPno())
                .title(dto.getTitle())
                .content(dto.getContent())
                // .writer(member)
                .build();
    }
}
