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
import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {
  // List<ProductDto> getList();

  CategoryPageResultDto<ProductDto, Object[]> getList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getFashionList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getMobileList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getElectricList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getGameList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getTribList(
    CategoryPageRequestDto requestDto
  );

  CategoryPageResultDto<ProductDto, Object[]> getEtcList(
    CategoryPageRequestDto requestDto
  );

  // ProductDto descList();
  List<ProductDto> pnodescList();

  // List<ProductDto> BiddingDescList(Long pno);
  List<ProductDto> BiddingDescList();

  List<ProductDto> CategoryList(Long cno);

  public default ProductDto entityToDto(
    Product product,
    List<ProductImage> productImages
  ) {
    ProductDto productDto = ProductDto
      .builder()
      .pno(product.getPno())
      .title(product.getTitle())
      .content(product.getContent())
      .writerName(product.getMember().getNickname())
      .startPrice(product.getStartPrice())
      .biddingDate(product.getBiddingDate())
      .category(product.getCategory().getCategoryName())
      .cno(product.getCategory().getCno())
      .createdDate(product.getCreatedDate())
      .lastModifiedDate(product.getLastModifiedDate())
      .build();

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
  public default Product dtoToEntity(ProductDto dto) {
    Member member = Member.builder().nickname(dto.getWriterName()).build();
    // bidding을 꼭 넣어야하는가?
    // Bidding bidding = Bidding.builder().build();
    Category category = Category
      .builder()
      .categoryName(dto.getCategory())
      .build();

    return Product
      .builder()
      .pno(dto.getPno())
      .title(dto.getTitle())
      .content(dto.getContent())
      .startPrice(dto.getStartPrice())
      .biddingDate(dto.getBiddingDate())
      .member(member)
      // .bidding(bidding)
      .category(category)
      .build();
  }

  // top6
  public default ProductDto entityToDto2(
    Product product,
    List<ProductImage> productImages
  ) {
    ProductDto productDto = ProductDto
      .builder()
      .pno(product.getPno())
      .title(product.getTitle())
      .content(product.getContent())
      .writerName(product.getMember().getNickname())
      .startPrice(product.getStartPrice())
      .biddingDate(product.getBiddingDate())
      .category(product.getCategory().getCategoryName())
      .cno(product.getCategory().getCno())
      .createdDate(product.getCreatedDate())
      .lastModifiedDate(product.getLastModifiedDate())
      .build();

    productImages.forEach(image -> {
      ProductImageDto imageDto = null;
      if (image.getProduct().getPno() == product.getPno()) {
        imageDto = new ProductImageDto();
        imageDto.setInum(image.getInum());
        imageDto.setPath(image.getPath());
        imageDto.setUuid(image.getUuid());
        imageDto.setImgName(image.getImgName());
        productDto.getProductImageDtos().add(imageDto);
      }
    });
    return productDto;
  }
}
