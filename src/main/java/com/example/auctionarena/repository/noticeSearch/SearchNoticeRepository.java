package com.example.auctionarena.repository.noticeSearch;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNoticeRepository {
    // 전체 조회
    Page<Object[]> getList(String type, String keyword, Pageable pageable);

    // 상세 조회
    List<Object[]> getRow(Long nno);
}
