package com.example.auctionarena.service;

import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import java.util.List;

public interface ProductService {
  List<ProductDto> getList();

  // entity => dto
  // public default ProductDto entityToDto(Product product, Member member, Long
  // replyCount) {
  public default ProductDto entityToDto(Product product, Long replyCnt) {
    return ProductDto
      .builder()
      .pno(product.getPno())
      .title(product.getTitle())
      .content(product.getContent())
      // .writerName(product.getMember().getNickname())
      .replyCnt(replyCnt != null ? replyCnt : 0)
      .startPrice(product.getStartPrice())
      .biddingDate(product.getBiddingDate())
      .cno(product.getCategory().getCno())
      .createdDate(product.getCreatedDate())
      .lastModifiedDate(product.getLastModifiedDate())
      .build();
  }

  // dto => entity
  public default Product dtoToEntity(ProductDto dto) {
    Member member = Member.builder().email(dto.getWriterName()).build();
    // bidding을 꼭 넣어야하는가?
    // Bidding bidding = Bidding.builder().build();
    Category category = Category.builder().cno(dto.getCno()).build();

    return Product
      .builder()
      .pno(dto.getPno())
      .title(dto.getTitle())
      .content(dto.getContent())
      .startPrice(dto.getStartPrice())
      .biddingDate(dto.getBiddingDate())
      // .member(member)
      // .bidding(bidding)
      .category(category)
      .build();
  }
}
