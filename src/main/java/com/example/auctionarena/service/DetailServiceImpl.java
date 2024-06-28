package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.BiddingRepository;
import com.example.auctionarena.repository.CategoryRepository;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.ProductImageRepository;
import com.example.auctionarena.repository.ProductRepository;
import com.example.auctionarena.repository.biddingDetail.BiddingDetailRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class DetailServiceImpl implements DetailService {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final CategoryRepository categoryRepository;
  private final MemberRepository memberRepository;
  private final BiddingRepository biddingRepository;
  private final BiddingDetailRepository biddingDetailRepository;

  @Override
  public ProductDto getRow(Long pno) {
    List<Object[]> result = productImageRepository.getProductRow(pno);
    List<Object[]> bid_result = biddingDetailRepository.getBiddingRow(pno);

    // Long bestPrice =
    // biddingRepository.findTop1ByProductOrderByBiddingPriceDesc(pno)

    // result의 값 첫번째 == product
    Product product = (Product) result.get(0)[0];
    // Bidding bidding = (Bidding) bid_result;

    // result 길이만큼 반복
    List<ProductImage> productImages = new ArrayList<>();
    result.forEach(arr -> {
      // productImage 개수 만큼 이미지 가져오기
      ProductImage productImage = (ProductImage) arr[1];
      productImages.add(productImage);
    });

    // 응찰
    List<Bidding> biddings = new ArrayList<>();
    bid_result.forEach(arr -> {
      Bidding bidding = (Bidding) arr[1];
      biddings.add(bidding);
      log.info("응찰 관련 데이터?: {}", bidding);
    });

    Long reviewCnt = (Long) result.get(0)[2];
    Long biddingCnt = (Long) result.get(0)[3];
    log.info("result : {}", result);
    log.info("응찰 개수 : {}", biddingCnt);
    // Long biddingCnt = 0L;

    return entityToDto(product, productImages, biddings, reviewCnt, biddingCnt);
  }

  // 제품 수정
  @Transactional
  @Override
  public Long productUpdate(ProductDto productDto) {
    Map<String, Object> entityMap = dtoToEntity(productDto);

    // product의 기존 이미지 삭제

    Product prod = productRepository.findById(productDto.getPno()).get();
    Product product = (Product) entityMap.get("product");
    log.info("title : {}", productDto.getTitle());
    log.info("content : {}", productDto.getContent());
    prod.setTitle(productDto.getTitle());
    prod.setContent(productDto.getContent());

    productRepository.save(prod);

    // 기존 이미지 삭제
    productImageRepository.deleteByProduct(product);

    // 새 이미지 삽입
    List<ProductImage> productImages = (List<ProductImage>) entityMap.get("imgList");
    log.info("product Image : {}", productImages);
    productImages.forEach(image -> productImageRepository.save(image));

    return product.getPno();
  }

  // 제품 삭제
  @Override
  public void productRemove(Long pno) {
    Product product = Product.builder().pno(pno).build();

    productRepository.delete(product);
  }

  // 제품 등록
  @Override
  public Long productRegister(ProductDto productDto) {
    // Optional<Member> member =
    // memberRepository.findById(productDto.getWriterEmail());

    // if (member.isPresent()) {
    // 닉네임 있을 때 진행
    Category category = categoryRepository
        .findByCategoryName(productDto.getCategory())
        .get();
    Member member = memberRepository.findByNickname(productDto.getWriterName());

    Map<String, Object> entityMap = dtoToEntity(productDto);
    // log.info("매핑은 됐나? : {}", entityMap);

    // 제품 등록
    Product product = (Product) entityMap.get("product");
    product.setCategory(category);
    product.setMember(member);
    // log.info("제품 등록 한다 이제 : {}", product);
    productRepository.save(product);
    // log.info("제품 저장했다 : {}", product);

    // 이미지 등록
    List<ProductImage> productImages = (List<ProductImage>) entityMap.get(
        "imgList");
    productImages.forEach(image -> productImageRepository.save(image));

    return product.getPno();
    // }
    // log.info("로그인 안되어있음");
    // return null;
  }

  // 카테고리 리스트 반환
  @Override
  public List<String> categoryNameList() {
    List<Category> list = categoryRepository.findAll();
    return list
        .stream()
        .map(entity -> entity.getCategoryName())
        .collect(Collectors.toList());
  }

  // 관련 제품 리스트 반환(카테고리 기준)
  @Override
  public List<ProductDto> getRelationList(Long pno) {
    // log.info("{}번 제품 관련 카테고리 가져오기", pno);

    // 관련 제품 정보 가져오기
    List<Product> products = productRepository.findByProductList(pno);
    log.info("검색한 카테고리의 제품 {}", products);

    // result 길이만큼 반복
    List<ProductImage> productImages = productImageRepository.getRelationRow(pno);
    log.info("{}번 제품과 같은 카테고리인 제품의 이미지 {}", pno, productImages);

    return products
        .stream()
        .map(entity -> entityToDto2(entity, productImages))
        .collect(Collectors.toList());
  }

}
