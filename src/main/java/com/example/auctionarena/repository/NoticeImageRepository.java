package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auctionarena.entity.NoticeImage;
import com.example.auctionarena.repository.noticeSearch.SearchNoticeRepository;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long>, SearchNoticeRepository {

}
