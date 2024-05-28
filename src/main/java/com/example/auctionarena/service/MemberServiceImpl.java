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
import com.example.auctionarena.dto.PasswordChangeDto;
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
        // 중복 닉네임 확인
        validateDuplicateNickname(insertDto.getNickname());
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

    @Override
    public void passwordFind(MemberDto findDto) throws IllegalStateException {
        // 이메일 확인
        log.info("비밀번호 찾기 요청 {}", findDto);

        Optional<Member> email = memberRepository.findByEmail(findDto.getEmail());
        if (email.isEmpty()) {
            throw new IllegalStateException("일치하는 회원이 없습니다.");
        } else {
            Member member = email.get();
            if (!member.getName().equals(findDto.getName())) {
                throw new IllegalStateException("이름이 다릅니다.");
            } else if (!member.getPhoneNumber().equals(findDto.getPhoneNumber())) {
                throw new IllegalStateException("전화번호가 다릅니다.");
            }
        }
    }

    @Override
    public void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(pDto.getEmail()).get();
        member.setPassword(passwordEncoder.encode(pDto.getNewPassword()));
        memberRepository.save(member);
    }

    // 중복 이메일 검사
    public void validateDuplicateEmail(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            throw new IllegalStateException("이메일");
        }
    }

    // 중복 닉네임 검사
    public void validateDuplicateNickname(String nickname) throws IllegalStateException {
        Optional<Member> result = memberRepository.findOptionalByNickname(nickname);

        if (result.isPresent()) {
            throw new IllegalStateException("닉네임");
        }
    }

}