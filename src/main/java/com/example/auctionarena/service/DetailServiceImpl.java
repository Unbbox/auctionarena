package com.example.auctionarena.service;

import com.example.auctionarena.dto.CategoryPageRequestDto;
import com.example.auctionarena.dto.CategoryPageResultDto;
import com.example.auctionarena.dto.PageRequestDto;
import com.example.auctionarena.dto.PageResultDto;
import com.example.auctionarena.dto.ProductDto;
import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.repository.CategoryRepository;
import com.example.auctionarena.repository.MemberRepository;
import com.example.auctionarena.repository.ProductImageRepository;
import com.example.auctionarena.repository.ProductRepository;
import java.util.ArrayList;
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

  // @Override
  // public List<ProductDto> getList() {
  // List<Product> list = productRepository.findAll();
  // List<ProductDto> productList = list
  // .stream()
  // .map(product -> entityToDto(product, null))
  // .collect(Collectors.toList());

  // return productList;
  // }
  // @Override
  // public CategoryPageResultDto<ProductDto, Object[]> getList(
  // CategoryPageRequestDto requestDto) {
  // Page<Object[]> result = productRepository.list(
  // requestDto.getType(),
  // requestDto.getKeyword(),
  // requestDto.getPageable(Sort.by("pno").descending()));

  // Function<Object[], ProductDto> fn = (entity -> entityToDto((Product)
  // entity[0], (Member) entity[1], null));
  // return new CategoryPageResultDto<>(result, fn);
  // }

  // 제품 상세 페이지
  // @Override
  // public ProductDto getRow(Long pno) {
  // Product entity = productRepository.findById(pno).get();

  // return entityToDto(entity, null, null);
  // }

  @Override
  public ProductDto getRow(Long pno) {
    List<Object[]> result = productImageRepository.getProductRow(pno);

    // result의 값 첫번째 == product
    Product product = (Product) result.get(0)[0];

    // result 길이만큼 반복
    List<ProductImage> productImages = new ArrayList<>();
    result.forEach(arr -> {
      // productImage 개수 만큼 이미지 가져오기
      ProductImage productImage = (ProductImage) arr[1];
      productImages.add(productImage);
    });

    Long reviewCnt = (Long) result.get(0)[2];

    return entityToDto(product, productImages, reviewCnt);
  }

  @Override
  public Long productRegister(ProductDto productDto) {
    // log.info("dto 전달 받음 : {}", productDto);

    // Optional<Member> member =
    // memberRepository.findById(productDto.getWriterEmail());

    // if (member.isPresent()) {
    // 닉네임 있을 때 진행
    Category category = categoryRepository.findByCategoryName(productDto.getCategory()).get();
    Member member = memberRepository.findByNickname(productDto.getWriterName());

    Map<String, Object> entityMap = dtoToEntity(productDto);
    log.info("매핑은 됐나? : {}", entityMap);

    // 제품 등록
    Product product = (Product) entityMap.get("product");
    product.setCategory(category);
    product.setMember(member);
    log.info("제품 등록 한다 이제 : {}", product);
    productRepository.save(product);
    // log.info("제품 저장했다 : {}", product);

    // 이미지 등록
    List<ProductImage> productImages = (List<ProductImage>) entityMap.get("imgList");
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
    return list.stream().map(entity -> entity.getCategoryName()).collect(Collectors.toList());
  }
}
