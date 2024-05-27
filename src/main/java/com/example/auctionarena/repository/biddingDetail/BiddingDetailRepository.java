package com.example.auctionarena.repository.biddingDetail;

import java.util.List;

public interface BiddingDetailRepository {

    // 제품 상세 페이지 조회 => 응찰 부분
    List<Object[]> getBiddingRow(Long pno);
}
