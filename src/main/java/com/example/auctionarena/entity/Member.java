package com.example.auctionarena.entity;

import com.example.auctionarena.constant.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // 소셜로그인
    private boolean fromSocial;
}
