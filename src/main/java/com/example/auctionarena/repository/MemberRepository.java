package com.example.auctionarena.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findOptionalByNickname(String nickname);
}
