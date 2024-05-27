package com.example.auctionarena.repository.biddingDetail;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import com.example.auctionarena.entity.Bidding;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.entity.QBidding;
import com.example.auctionarena.entity.QComment;
import com.example.auctionarena.entity.QProduct;
import com.example.auctionarena.entity.QProductImage;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BiddingDetailRepositoryImpl extends QuerydslRepositorySupport
        implements BiddingDetailRepository {

    public BiddingDetailRepositoryImpl() {
        super(Bidding.class);
    }

    @Override
    public List<Object[]> getBiddingRow(Long pno) {
        // Q 클래스 사용
        QProduct product = QProduct.product;
        QBidding bidding = QBidding.bidding;

        // 응찰
        JPQLQuery<Bidding> query = from(bidding);
        query.leftJoin(product).on(bidding.product.eq(product));

        JPQLQuery<Tuple> tuple = query.select(product, bidding)
                .where(bidding.product.pno.eq(pno))
                .orderBy(bidding.createdDate.desc());

        List<Tuple> result = tuple.fetch();

        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());
    }
}
