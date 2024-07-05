package com.example.auctionarena.service;

import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.dto.PasswordChangeDto;
import com.example.auctionarena.entity.Member;

public interface MemberService {
    // 회원가입
    String signup(MemberDto insertDto) throws IllegalStateException;

    // 비밀번호 찾기
    void passwordFind(MemberDto findDto) throws IllegalStateException;

    // 비밀번호 수정
    void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException;

    // 회원정보 찾기
    void accountCheck(MemberDto checkDto) throws IllegalStateException;

    // 회원정보 수정
    void editAccountInfo(MemberDto infoDto);

    // 회원탈퇴
    void leave(String email) throws IllegalStateException;;

    public default Member dtoToEntity(MemberDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .zonecode(dto.getZonecode())
                .addr(dto.getAddr())
                .phoneNumber(dto.getPhoneNumber())
                .age(dto.getAge())
                .gender(dto.getGender())
                .role(dto.getRole())
                .build();
    }

    public default MemberDto entityToDto(Member member) {
        return MemberDto.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .zonecode(member.getZonecode())
                .addr(member.getAddr())
                .phoneNumber(member.getPhoneNumber())
                .age(member.getAge())
                .gender(member.getGender())
                .role(member.getRole())
                .build();
    }

}
