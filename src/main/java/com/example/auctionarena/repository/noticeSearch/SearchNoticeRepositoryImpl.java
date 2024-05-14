package com.example.auctionarena.repository.noticeSearch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.auctionarena.entity.Notice;
import com.example.auctionarena.entity.QMember;
import com.example.auctionarena.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository {

    public SearchNoticeRepositoryImpl() {
        super(Notice.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("Notice + Member join");

        QNotice notice = QNotice.notice;
        QMember member = QMember.member;

        // 쿼리
        JPQLQuery<Notice> query = from(notice);
        query.leftJoin(notice.writer, member);

        JPQLQuery<Tuple> tuple = query.select(notice, member);

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
        if (type.contains("w")) {
            conditionBuilder.or(member.nickname.contains(keyword));
        }
        builder.and(conditionBuilder);
        tuple.where(builder);

        // 페이지 나누기 sort 지정
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Notice> orderByExpression = new PathBuilder<>(Notice.class, "notice");
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

}
