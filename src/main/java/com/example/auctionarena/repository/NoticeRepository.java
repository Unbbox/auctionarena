package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
