package com.example.auctionarena.dto;

import java.time.LocalDateTime;

import com.example.auctionarena.constant.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {

    private Long mid;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    private String name;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;

    private String phoneNumber;

    private String addr;

    private String zonecode;

    // 나이, 성별 추가
    private Integer age;

    private String gender;

    private MemberRole role;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    // 소셜로그인
    private boolean fromSocial;
}
