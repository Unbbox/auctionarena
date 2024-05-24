package com.example.auctionarena.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auctionarena.constant.MemberRole;
import com.example.auctionarena.dto.AuthMemberDto;
import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 {}", username);
        Optional<Member> result = memberRepository.findByEmail(username);
        if (!result.isPresent())
            throw new UsernameNotFoundException("Check Email");
        Member member = result.get();

        return new AuthMemberDto(entityToDto(member));
    }

    @Override
    public String signup(MemberDto insertDto) throws IllegalStateException {
        log.info("회원가입 요청 {}", insertDto);

        // 중복 이메일 확인
        validateDuplicateEmail(insertDto.getEmail());
        // 비밀번호 암호화
        insertDto.setPassword(passwordEncoder.encode(insertDto.getPassword()));
        // 권한 부여
        insertDto.setRole(MemberRole.MEMBER);

        // Member member = Member.builder()
        // .email(insertDto.getEmail())
        // .password(passwordEncoder.encode(insertDto.getPassword()))
        // .name(insertDto.getName())
        // .nickname(insertDto.getNickname())
        // .phoneNumber(insertDto.getPhoneNumber())
        // .addr(insertDto.getAddr())
        // .zonecode(insertDto.getZonecode())
        // .role(MemberRole.MEMBER)
        // .build();

        return memberRepository.save(dtoToEntity(insertDto)).getEmail();
    }

    // 중복 이메일 검사
    public void validateDuplicateEmail(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
