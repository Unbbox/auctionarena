package com.example.auctionarena.service;

import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.entity.Member;

public interface MemberService {
    // 회원가입
    String signup(MemberDto insertDto) throws IllegalStateException;

    // 주소 수정
    // void addrUpdate(MemberDto upAddrDto);
    // 비밀번호 수정
    // void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException;
    // 회원탈퇴
    // void leave(MemberDto leavMemberDto);

    public default Member dtoToEntity(MemberDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .zonecode(dto.getZonecode())
                .addr(dto.getAddr())
                .phoneNumber(dto.getPhoneNumber())
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
                .role(member.getRole())
                .build();
    }

}
