package com.example.auctionarena.repository.productSearch;

import com.example.auctionarena.entity.Category;
import com.example.auctionarena.entity.Product;
import com.example.auctionarena.entity.ProductImage;
import com.example.auctionarena.entity.QBidding;
import com.example.auctionarena.entity.QCategory;
import com.example.auctionarena.entity.QComment;
import com.example.auctionarena.entity.QMember;
import com.example.auctionarena.entity.QProduct;
import com.example.auctionarena.entity.QProductImage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    // super(Product.class);
    super(ProductImage.class);
    // TODO Auto-generated constructor stub
  }

  @Override
  public Page<Object[]> list(
    String type,
    String keyword,
    // Long cno,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    // JPQLQuery<Product> query = from(product);
    // query.leftJoin(product.member, member);

    // JPQLQuery<Tuple> tuple = query.select(
    // product,
    // member,
    // JPAExpressions
    // .select(category.cno)
    // .from(category)
    // .where(category.category.eq(product.category))
    // );

    JPQLQuery<ProductImage> query = from(productImage);
    query.leftJoin(product).on(productImage.product.eq(product));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(comment.product.eq(productImage.product))
      )
      .where(
        productImage.inum.in(
          JPAExpressions
            .select(productImage.inum.min())
            .from(productImage)
            .groupBy(productImage.product.pno)
        )
      );

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

    // 카테고리가 지정된 경우
    // BooleanBuilder cateBuilder = new BooleanBuilder();
    // if (cno != null) {
    // cateBuilder.or(product.category.eq(category));
    // }
    // builder.and(cateBuilder);
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
