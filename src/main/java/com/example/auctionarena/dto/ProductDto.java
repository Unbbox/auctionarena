package com.example.auctionarena.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private Long pno;

  @NotBlank(message = "제목을 입력해주세요")
  private String title; // 상품 제목

  @NotBlank(message = "내용을 입력해주세요")
  private String content; // 게시글 내용

  // 카테고리 관계
  private String category; // 카테고리 분류
  private Long cno;

  // 멤버 관계
  private String writerName; // 작성자 이름

  private Long replyCount; // 게시글 댓글 수

  @NotBlank(message = "경매 시작 가격을 입력해주세요")
  private Long startPrice; // 경매 시작 가격

  @NotBlank(message = "경매 진행 기간을 입력해주세요")
  private Long biddingDate; // 경매 진행 기간

  // 리뷰 개수
  private Long replyCnt;

  // 제품이미지 리스트
  @Builder.Default
  private List<ProductImageDto> productImageDtos = new ArrayList<>();

  // 응찰 관련
  private Long biddingPrice;
  private LocalDateTime biddingTime;

  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
