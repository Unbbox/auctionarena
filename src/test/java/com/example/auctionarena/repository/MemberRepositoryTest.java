package com.example.auctionarena.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.auctionarena.constant.MemberRole;
import com.example.auctionarena.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void memberInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1111")
                    .name("홍길동" + i)
                    .nickname("USER" + i)
                    .zonecode("0000" + i)
                    .addr("서울시 종로구 종로" + i + "길")
                    .phoneNumber("010-1111-111" + i)
                    .role(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }
}
