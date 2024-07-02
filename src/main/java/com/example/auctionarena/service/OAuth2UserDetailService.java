package com.example.auctionarena.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import com.fasterxml.jackson.databind.ObjectMapper;

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
        // 소셜로그인 공통인증 방식으로 로그인하면 뜨는 정보들
        log.info("========================================");
        log.info("userRequest : {}", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName : {}", clientName);
        log.info("Token : {}", userRequest.getAccessToken());
        log.info("Client : {}", userRequest.getClientRegistration());
        log.info("========================================");
        OAuth2User oAuth2User = super.loadUser(userRequest);


        try {
            String getAttr = new ObjectMapper().writeValueAsString(oAuth2User.getAttributes());
            log.info("getAttr : {}", getAttr);
            log.info("========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Member member;
        // 네이버 로그인
        if (clientName.equals("naver")) {
            log.info("response {}", oAuth2User.getAttributes().get("response"));
            log.info("========================================");
            Map<String, String> map = (Map<String, String>) oAuth2User.getAttributes().get("response");
            String email = map.get("email");
            String name = map.get("name");
            String phoneNumber = map.get("mobile").replace("-", "");
            log.info("info {} {} {}", email, name, phoneNumber);
            log.info("========================================");

            member = saveSocialMember(email, name, phoneNumber);

        } else if (clientName.equals("kakao")) {
            // 카카오 로그인
            Map<String, String> map = (Map<String, String>) oAuth2User.getAttributes().get("kakao_account");
            String email = map.get("email");
            String name = map.get("name");
            String phoneNumber = map.get("phone_number").replace("-", "").replace("+82 ", "0") ;
            log.info("info : {} , {}, {}", email, name, phoneNumber);
            log.info("========================================");

            member = saveSocialMember(email, name, phoneNumber);

            } else {
            // 구글 로그인
            // 소셜 로그인에서 제공되는 이메일, 이름 가져오기
            member = saveSocialMember(oAuth2User.getAttribute("email"), oAuth2User.getAttribute("name"));
        }
        
        return new AuthMemberDto(entityToDto(member), true);

    }

    private Member saveSocialMember(String email, String name){
        // 이메일로 기존 회원 정보 확인
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);
        if (result.isPresent()) {
            // 기존 회원이 존재하면 해당 회원 정보 반환
            return result.get();
        } 

            // 기존 회원이 없으면 새로운 회원 생성
          Member member = Member.builder()
                    .email(email)
                    // 임시 비밀번호 생성
                    .name(name)
                    .password(passwordEncoder.encode("1111"))
                    .nickname(generateNickname())
                    .role(MemberRole.MEMBER)
                    .fromSocial(true)
                    .build();
            memberRepository.save(member);

            return member;
    }

    private Member saveSocialMember(String email, String name, String phoneNumber){
        // 이메일로 기존 회원 정보 확인
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            // 기존 회원이 존재하면 해당 회원 정보 반환
            return result.get();
        } 

            // 기존 회원이 없으면 새로운 회원 생성
            Member member = Member.builder()
                    .email(email)
                    .name(name)
                    // 임시 비밀번호 생성
                    .password(passwordEncoder.encode("1111"))
                    .nickname(generateNickname())
                    .phoneNumber(phoneNumber)
                    .role(MemberRole.MEMBER)
                    .fromSocial(true)
                    .build();
            memberRepository.save(member);

        return member;
    }

    private MemberDto entityToDto(Member member) {
        // MemberDto 생성
        return MemberDto.builder()
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
