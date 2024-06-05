package com.example.auctionarena.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = { "member", "product" })
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @SequenceGenerator(name = "product_comment_seq_gen", sequenceName = "comment_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_comment_seq_gen")
    @Id
    private Long commentNo;

    private String text; // 댓글 내용

    // 멤버
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 제품
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
