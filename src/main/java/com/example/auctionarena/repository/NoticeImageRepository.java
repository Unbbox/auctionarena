package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.NoticeImage;
import com.example.auctionarena.repository.noticeSearch.SearchNoticeRepository;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long>, SearchNoticeRepository {

    @Modifying
    @Query("delete from NoticeImage ni where ni.notice = :notice")
    void deleteByNotice(Notice notice);
}
