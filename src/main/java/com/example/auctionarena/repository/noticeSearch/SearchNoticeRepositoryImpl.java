package com.example.auctionarena.repository.noticeSearch;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.QMember;
import com.example.auctionarena.entity.QNotice;
import com.example.auctionarena.entity.QNoticeImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository {

    public SearchNoticeRepositoryImpl() {
        super(Notice.class);
    }

    @Override
    public Page<Object[]> getList(String type, String keyword, Pageable pageable) {
        log.info("Notice + Member + NoticeImage join");

        QNotice notice = QNotice.notice;
        QMember member = QMember.member;
        QNoticeImage noticeImage = QNoticeImage.noticeImage;

        // Notice와 NoticeImage, member를 조인
        JPQLQuery<Notice> query = from(notice)
                .leftJoin(member).on(notice.member.eq(member))
                .leftJoin(noticeImage).on(notice.eq(noticeImage.notice));

        JPQLQuery<Tuple> tuple = query.select(notice, member, noticeImage)
                .where(noticeImage.ninum.in(
                        JPAExpressions.select(noticeImage.ninum.min()).from(noticeImage)
                                .groupBy(noticeImage.notice.nno))
                        .or(noticeImage.ninum.isNull()));

        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(notice.nno.gt(0L));

        // 검색 타입
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(notice.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(notice.content.contains(keyword));
        }

        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Notice> orderByExpression = new PathBuilder<>(Notice.class,
                    "notice");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        // 페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        // 전체 개수
        long count = tuple.fetchCount();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public List<Object[]> getRow(Long nno) {
        log.info("상세조회 요청");

        QNotice notice = QNotice.notice;
        QMember member = QMember.member;
        QNoticeImage noticeImage = QNoticeImage.noticeImage;

        // Notice와 NoticeImage를 조인
        JPQLQuery<Notice> query = from(notice)
                .leftJoin(member).on(notice.member.eq(member))
                .leftJoin(noticeImage).on(notice.eq(noticeImage.notice));

        // 쿼리 생성
        JPQLQuery<Tuple> tuple = query.select(notice, member.nickname, noticeImage)
                .where(notice.nno.eq(nno))
                .orderBy(noticeImage.ninum.desc());

        // 결과 조회
        List<Tuple> result = tuple.fetch();

        // 결과를 List<Object[]>로 변환
        return result.stream().map(Tuple::toArray).collect(Collectors.toList());
    }

}
