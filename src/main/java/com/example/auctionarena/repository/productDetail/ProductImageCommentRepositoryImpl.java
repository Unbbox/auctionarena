package com.example.auctionarena.repository.productDetail;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
public class ProductImageCommentRepositoryImpl extends QuerydslRepositorySupport
        implements ProductImageCommentRepository {

    public ProductImageCommentRepositoryImpl() {
        super(ProductImage.class);
    }

    @Override
    public List<Object[]> getProductRow(Long pno) {
        // Q 클래스 사용
        QProduct product = QProduct.product;
        QComment comment = QComment.comment;
        QProductImage productImage = QProductImage.productImage;
        // QBidding bidding = QBidding.bidding;

        //
        JPQLQuery<ProductImage> query = from(productImage);
        query.leftJoin(product).on(productImage.product.eq(product));
        // query.leftJoin(bidding).on(productImage.product.eq(bidding.product));

        // 응찰
        // JPQLQuery<Bidding> bid_query = from(bidding);
        // bid_query.leftJoin(product).on(bidding.product.eq(product));

        // JPQLQuery<Tuple> tuple = query.select(product, productImage, bidding,
        // JPAExpressions.select(comment.countDistinct()).from(comment)
        // .where(comment.product.eq(productImage.product)))
        // .where(productImage.product.pno.eq(pno))
        // .orderBy(productImage.inum.desc());
        JPQLQuery<Tuple> tuple = query.select(product, productImage,
                JPAExpressions.select(comment.countDistinct()).from(comment)
                        .where(comment.product.eq(productImage.product))
        // 추후 경매 횟수 확인할 때 사용
        // JPAExpressions.select(bidding.countDistinct()).from(bidding)
        // .where(bidding.product.eq(product))
        )
                .where(productImage.product.pno.eq(pno))
                .orderBy(productImage.inum.desc());

        List<Tuple> result = tuple.fetch();
        log.info("제품 판매 페이지 tuple: {}", tuple);
        log.info("제품 판매 페이지 result: {}", result);

        return result.stream().map(t -> t.toArray()).collect(Collectors.toList());
    }

}
