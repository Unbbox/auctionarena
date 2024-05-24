package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.dto.ProductImageDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DetailService {
  // List<ProductDto> getList();

  // CategoryPageResultDto<ProductDto, Object[]> getList(CategoryPageRequestDto
  // requestDto);

  // 제품 상세 페이지 요청
  ProductDto getRow(Long pno);

  // 제품 등록
  Long productRegister(ProductDto productDto);

  // 카테고리 리스트 반환
  List<String> categoryNameList();

  // entity => dto
  // public default ProductDto entityToDto(Product product, Member member, Long
  // replyCount) {
  public default ProductDto entityToDto(
      Product product,
      List<ProductImage> productImages,
      Long replyCnt) {
    ProductDto productDto = ProductDto
        .builder()
        .pno(product.getPno())
        .title(product.getTitle())
        .content(product.getContent())
        .writerName(product.getMember().getNickname())
        .replyCnt(replyCnt != null ? replyCnt : 0)
        .startPrice(product.getStartPrice())
        .biddingDate(product.getBiddingDate())
        .category(product.getCategory().getCategoryName())
        .createdDate(product.getCreatedDate())
        .lastModifiedDate(product.getLastModifiedDate())
        .build();

    // 제품 상세 페이지의 이미지
    List<ProductImageDto> productImageDtos = productImages
        .stream()
        .map(productImage -> {
          return ProductImageDto
              .builder()
              .inum(productImage.getInum())
              .uuid(productImage.getUuid())
              .imgName(productImage.getImgName())
              .path(productImage.getPath())
              .build();
        })
        .collect(Collectors.toList());

    productDto.setProductImageDtos(productImageDtos);
    return productDto;
  }

  // dto => entity
  public default Map<String, Object> dtoToEntity(ProductDto dto) {

    Map<String, Object> entityMap = new HashMap<>();

    Member member = Member.builder().nickname(dto.getWriterName()).build();
    Category category = Category.builder().categoryName(dto.getCategory()).build();

    // product entity 생성
    Product product = Product.builder()
        .pno(dto.getPno())
        .title(dto.getTitle())
        .content(dto.getContent())
        .startPrice(dto.getStartPrice())
        .biddingDate(dto.getBiddingDate())
        .member(member)
        .category(category)
        .build();
    entityMap.put("product", product);
    // entityMap.put("member", member);

    List<ProductImageDto> productImageDtos = dto.getProductImageDtos();

    if (productImageDtos != null && productImageDtos.size() > 0) {
      List<ProductImage> productImages = productImageDtos.stream().map(pDto -> {
        ProductImage productImage = ProductImage.builder()
            .imgName(pDto.getImgName())
            .uuid(pDto.getUuid())
            .path(pDto.getPath())
            .product(product)
            .build();
        return productImage;
      }).collect(Collectors.toList());

      entityMap.put("imgList", productImages);
    }

    return entityMap;

    // ** Map<String,Object>가 아닌 Product일 때의 코드
    // Member member = Member.builder().nickname(dto.getWriterName()).build();
    // // bidding을 꼭 넣어야하는가?
    // // Bidding bidding = Bidding.builder().build();
    // Category category = Category
    // .builder()
    // .categoryName(dto.getCategory())
    // .build();

    // return Product
    // .builder()
    // .pno(dto.getPno())
    // .title(dto.getTitle())
    // .content(dto.getContent())
    // .startPrice(dto.getStartPrice())
    // .biddingDate(dto.getBiddingDate())
    // .member(member)
    // // .bidding(bidding)
    // .category(category)
    // .build();
  }
}
