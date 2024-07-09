package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Member;

public interface MypageRepository extends JpaRepository<Member, Long> {

}
