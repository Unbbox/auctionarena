package com.example.auctionarena.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.auctionarena.constant.MemberRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = { "noticeList", "productList", "biddingList", "commentList" })
@Data
@Entity
public class Member extends BaseEntity {

    @SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
    @Id
    private Long mid;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String name;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String addr;

    @Column(nullable = true)
    private String zonecode;

    // 나이, 성별 추가
    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    private String gender;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // 소셜로그인
    private boolean fromSocial;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notice> noticeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bidding> biddingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<Comment> commentList = new ArrayList<>();
}
