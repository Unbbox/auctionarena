package com.example.auctionarena.repository.noticeSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNoticeRepository {
    Page<Object[]> list(String type, String keyword, Pageable pageable);
}
