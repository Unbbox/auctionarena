package com.example.auctionarena.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = { "member", "category", "productImages", "comments", "biddings" })
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

  @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
  @Id
  private Long pno;

  private String title;

  private String content;

  private Long startPrice;

  private Long biddingDate;

  // 멤버 관련
  // member.nickname
  @ManyToOne(fetch = FetchType.LAZY) // , cascade = CascadeType.ALL
  private Member member;

  // 응찰 관련
  // bidding.biddingPrice , bidding.biddingDate
  // @ManyToOne(fetch = FetchType.LAZY)
  // private Bidding bidding;

  // 카테고리 관련
  @ManyToOne(fetch = FetchType.LAZY) // , cascade = CascadeType.ALL
  private Category category;
  // // 이미지 관련 리스트
  // @Builder.Default
  // @OneToMany(mappedBy = "product")
  // private List<ProductImage> productImages = new ArrayList<>();
  // 이미지 관련 리스트
  // private ProductImage productImage;

  // @Builder.Default
  // @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval
  // = true)
  // private List<ProductImage> productImages = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Bidding> biddings = new ArrayList<>();
}
