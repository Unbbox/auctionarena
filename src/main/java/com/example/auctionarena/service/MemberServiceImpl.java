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

import jakarta.transaction.Transactional;
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
        log.info("로그인 요청");
        Optional<Member> result = memberRepository.findByEmail(username);
        if (!result.isPresent())
            throw new UsernameNotFoundException("Check Email");
        Member member = result.get();

        return new AuthMemberDto(entityToDto(member), false);
    }

    @Override
    public String signup(MemberDto insertDto) throws IllegalStateException {
        log.info("회원가입 요청");

        // 중복 이메일 확인
        validateDuplicateEmail(insertDto.getEmail());
        // 중복 닉네임 확인
        validateDuplicateNickname(insertDto.getNickname());
        // 비밀번호 암호화
        insertDto.setPassword(passwordEncoder.encode(insertDto.getPassword()));
        // 권한 부여
        insertDto.setRole(MemberRole.MEMBER);

        return memberRepository.save(dtoToEntity(insertDto)).getEmail();
    }

    @Override
    public void passwordFind(MemberDto findDto) throws IllegalStateException {
        // 이메일 확인
        log.info("비밀번호 찾기 요청");

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

    @Transactional
    @Override
    public void leave(String email) throws IllegalStateException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new IllegalStateException("일치하는 회원이 없습니다.");
        }
        memberRepository.delete(member.get());
    }

    @Override
    public void accountCheck(MemberDto checkDto) throws IllegalStateException {
        // 이메일 확인
        log.info("비밀번호 찾기 요청");

        Optional<Member> email = memberRepository.findByEmail(checkDto.getEmail());
        Member member = email.get();
        if (email.isEmpty()) {
            throw new IllegalStateException("이메일이 다릅니다.");
        } else {
            if (!passwordEncoder.matches(checkDto.getPassword(), member.getPassword())) {
                throw new IllegalStateException("비밀번호가 다릅니다.");
            }
        }
    }

    @Override
    public void editAccountInfo(MemberDto infoDto) {
        log.info("회원정보 수정 요청");

        Member member = memberRepository.findByEmail(infoDto.getEmail()).get();

        if (!infoDto.getNickname().equals(member.getNickname())) {
            validateDuplicateNickname(infoDto.getNickname());
        }

        member.setName(infoDto.getName());
        member.setNickname(infoDto.getNickname());
        member.setZonecode(infoDto.getZonecode());
        member.setAddr(infoDto.getAddr());
        member.setPhoneNumber(infoDto.getPhoneNumber());
        member.setAge(infoDto.getAge());
        member.setGender(infoDto.getGender());

        memberRepository.save(member);
    }

    // 중복 이메일 검사
    public void validateDuplicateEmail(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }

    // 중복 닉네임 검사
    public void validateDuplicateNickname(String nickname) throws IllegalStateException {
        Optional<Member> result = memberRepository.findOptionalByNickname(nickname);

        if (result.isPresent()) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }

}