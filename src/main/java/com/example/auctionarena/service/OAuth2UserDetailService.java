package com.example.auctionarena.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.auctionarena.constant.MemberRole;
import com.example.auctionarena.dto.AuthMemberDto;
import com.example.auctionarena.dto.MemberDto;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class OAuth2UserDetailService extends DefaultOAuth2UserService {
    // OAuth 2.0 인증을 통해 인증된 사용자의 정보를 관리하고 저장
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 소셜 로그인에서 제공되는 이메일 가져오기
        String email = oauth2User.getAttribute("email");

        // 소셜 로그인에서 제공되는 액세스 토큰 가져오기
        String accessToken = userRequest.getAccessToken().getTokenValue();

        // 이메일로 기존 회원 정보 확인
        Optional<Member> existingMember = memberRepository.findByEmailAndFromSocial(email, true);
        Member member;
        if (existingMember.isPresent()) {
            // 기존 회원이 존재하면 해당 회원 정보 반환
            member = existingMember.get();
        } else {
            // 기존 회원이 없으면 새로운 회원 생성
            member = new Member();
            member.setEmail(email);
            // 임시 비밀번호 생성 혹은 소셜에서 제공되는 비밀번호로 설정
            member.setPassword(passwordEncoder.encode(accessToken));
            // 소셜 회원은 자동 생성된 닉네임을 사용
            member.setNickname(generateNickname());
            member.setRole(MemberRole.MEMBER);
            member.setFromSocial(true);
            memberRepository.save(member);
        }

        // MemberDto 생성
        MemberDto memberDto = MemberDto.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .addr(member.getAddr())
                .zonecode(member.getZonecode())
                .role(member.getRole())
                .createdDate(member.getCreatedDate())
                .lastModifiedDate(member.getLastModifiedDate())
                .fromSocial(member.isFromSocial())
                .build();

        // AuthMemberDto 생성
        List<SimpleGrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
        AuthMemberDto authMemberDto = new AuthMemberDto(memberDto.getEmail(), memberDto.getPassword(),
                memberDto.isFromSocial(), authorities);
        authMemberDto.setMemberDto(memberDto);

        return new DefaultOAuth2User(authMemberDto.getAuthorities(), oauth2User.getAttributes(), "email");
    }

    // 닉네임 생성 메서드
    private String generateNickname() {
        String baseNickname = "SocialUser";
        String nickname;
        int count = 1;
        do {
            nickname = baseNickname + count;
            count++;
        } while (memberRepository.findByNickname(nickname) != null);
        return nickname;
    }

}
