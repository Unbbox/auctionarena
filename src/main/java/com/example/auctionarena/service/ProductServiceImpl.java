package com.example.auctionarena.service;

import com.example.auctionarena.dto.BiddingDto;
import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.NoticeImage;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.BiddingRepository;
import com.example.auctionarena.repository.CategoryRepository;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.ProductImageRepository;
import com.example.auctionarena.repository.ProductRepository;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductImageRepository productImageRepository;
  private final MemberRepository membereRepository;
  private final BiddingRepository biddingRepository;

  // @Override
  // public List<ProductDto> getList() {
  // List<Product> list = productRepository.findAll();
  // List<ProductDto> productList = list
  // .stream()
  // .map(product -> entityToDto(product, null))
  // .collect(Collectors.toList());

  // return productList;
  // }
  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.list(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
    // (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getMobileList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.mobilecatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getFashionList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.fashioncatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getElectricList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.electriccatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getGameList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.gamecatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getTribList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.tribcatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public CategoryPageResultDto<ProductDto, Object[]> getEtcList(
    CategoryPageRequestDto requestDto
  ) {
    Page<Object[]> result = productImageRepository.etccatelist(
      requestDto.getType(),
      requestDto.getKeyword(),
      // requestDto.getCno(),
      // requestDto.getCategory(),
      requestDto.getPageable(Sort.by("pno").descending())
    );

    Function<Object[], ProductDto> fn =
      (
        entity ->
          entityToDto(
            (Product) entity[0],
            (List<ProductImage>) Arrays.asList((ProductImage) entity[1])
          )
      );
    return new CategoryPageResultDto<>(result, fn);
  }

  @Override
  public List<ProductDto> pnodescList() {
    List<Product> list = productRepository.findTop6ByOrderByPnoDesc();

    List<ProductImage> productImages = productImageRepository.findImage();

    log.info("제품 정보 {}", list);
    log.info("제품 이미지 {}", productImages);
    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> BiddingDescList() {
    List<Product> list = productRepository.findTop6ByOrderByBiddingCntDesc();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomList() {
    List<Product> list = productRepository.findRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomFashionList() {
    List<Product> list = productRepository.findfashionRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomMobileList() {
    List<Product> list = productRepository.findmobileRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomElectricList() {
    List<Product> list = productRepository.findelectricRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomGameList() {
    List<Product> list = productRepository.findgameRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomTribList() {
    List<Product> list = productRepository.findtribRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductDto> RandomEtcList() {
    List<Product> list = productRepository.findetcRandomList();
    List<ProductImage> productImages = productImageRepository.findImage();

    return list
      .stream()
      .map(entity -> entityToDto2(entity, productImages))
      .collect(Collectors.toList());
  }
}
