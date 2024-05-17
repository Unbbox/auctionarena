package com.example.auctionarena.repository.productSearch;

import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.QMember;
import com.example.auctionarena.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class SearchProductRepositoryImpl
  extends QuerydslRepositorySupport
  implements SearchProductRepository {

  public SearchProductRepositoryImpl() {
    super(Product.class);
    //TODO Auto-generated constructor stub
  }

  @Override
  public Page<Object[]> list(String type, String keyword, Pageable pageable) {
    // log.info("Board + Reply + Member join");

    QMember member = QMember.member;

    JPQLQuery<Product> query = from(product);
    query.leftJoin(product.member, member);

    JPQLQuery<Tuple> tuple = query.select(product, member);

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // WHERE gno > 0 AND title LIKE '%Title%' OR content LIKE '%content%'
    // gno > 0

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    if (type.contains("t")) {
      conditionBuilder.or(product.title.contains(keyword));
    }
    if (type.contains("c")) {
      conditionBuilder.or(product.content.contains(keyword));
    }
    builder.and(conditionBuilder);
    tuple.where(builder);

    Sort sort = pageable.getSort();
    sort
      .stream()
      .forEach(order -> {
        Order direction = order.isAscending() ? Order.ASC : Order.DESC;
        String prop = order.getProperty();

        PathBuilder<Product> orderByExpression = new PathBuilder<>(
          Product.class,
          "product"
        );
        tuple.orderBy(
          new OrderSpecifier(direction, orderByExpression.get(prop))
        );
      });
    // 페이지 처리
    tuple.offset(pageable.getOffset());
    tuple.limit(pageable.getPageSize());

    List<Tuple> result = tuple.fetch();

    // 전체 개수
    long count = tuple.fetchCount();

    List<Object[]> list = result
      .stream()
      .map(t -> t.toArray())
      .collect(Collectors.toList());

    return new PageImpl<>(list, pageable, count);
  }
}
