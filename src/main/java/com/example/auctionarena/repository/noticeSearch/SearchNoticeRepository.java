package com.example.auctionarena.repository.noticeSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNoticeRepository {
    // 전체 조회
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    // 상세 조회
    Object[] getRow(Long nno);
}
