package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByNickname(String nickname);
}
