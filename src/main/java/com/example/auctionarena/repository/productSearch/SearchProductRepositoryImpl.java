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
  public Page<Object[]> list(String type, String keyword, Pageable pageable) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

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

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> mobilecatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 2))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> fashioncatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 1))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> electriccatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 3))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> gamecatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 4))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> tribcatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 5))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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

  @Override
  public Page<Object[]> etccatelist(
    String type,
    String keyword,
    Pageable pageable
  ) {
    QProduct product = QProduct.product;
    QMember member = QMember.member;
    QCategory category = QCategory.category;
    QProductImage productImage = QProductImage.productImage;
    QComment comment = QComment.comment;

    JPQLQuery<ProductImage> query = from(productImage);
    query
      .leftJoin(product)
      .on(productImage.product.eq(product))
      .leftJoin(category)
      .on(product.category.cno.eq(category.cno));

    JPQLQuery<Tuple> tuple = query
      .select(
        product,
        productImage,
        category,
        JPAExpressions
          .select(comment.countDistinct())
          .from(comment)
          .where(
            comment.product.eq(productImage.product)
            // .and(product.category.cno.eq(category.cno))
          )
      )
      .where(
        productImage.inum
          .in(
            JPAExpressions
              .select(productImage.inum.min())
              .from(productImage)
              .groupBy(productImage.product.pno)
          )
          .and(product.category.cno.eq((long) 6))
      );

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(product.pno.gt(0L));

    // 검색 타입이 있는 경우
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    conditionBuilder.or(product.title.contains(keyword));
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
