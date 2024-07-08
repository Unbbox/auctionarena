package com.example.auctionarena.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @ToString(exclude = { "product", "member" })
@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wish extends BaseEntity {

  @SequenceGenerator(
    name = "wish_seq_gen",
    sequenceName = "wish_gen",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "wish_seq_gen"
  )
  @Id
  private Long wno;

  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;
}
