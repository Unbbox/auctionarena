package com.example.auctionarena.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.repository.noticeSearch.SearchNoticeRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, SearchNoticeRepository {

}
