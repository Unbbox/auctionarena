package com.example.auctionarena.repository.productDetail;

import java.util.List;

public interface ProductImageCommentRepository {
    // 제품 상세 페이지 조회
    List<Object[]> getProductRow(Long pno);
}
