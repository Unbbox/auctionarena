package com.example.auctionarena.repository;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.auctionarena.constant.MemberRole;
import com.example.auctionarena.entity.Member;
import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.Product;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String[] genders = {"male", "female"};


    @Test
    public void memberInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Integer age = (int) ((Math.random()*70)+10);

            String gender = genders[(int)(Math.random()*2)];
        

            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("홍길동" + i)
                    .nickname("USER" + i)
                    .zonecode("0000" + i)
                    .addr("서울시 종로구 종로" + i + "길")
                    .phoneNumber("0101111111" + i)
                    .age(age)
                    .gender(gender)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void adminInsert() {
        Member member = Member.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("1111"))
                .name("관리자")
                .nickname("관리자")
                .zonecode("0000")
                .addr("서울시 종로구 종로34길")
                .phoneNumber("01012341234")
                .role(MemberRole.ADMIN)
                .build();
        memberRepository.save(member);
    }

    @Commit
    @Transactional
    @Test
    public void leaveMemberTest() {
        Member member = Member.builder()
                .mid(100L)
                .build();

        // 회원 삭제
        memberRepository.delete(member);
    }
}
