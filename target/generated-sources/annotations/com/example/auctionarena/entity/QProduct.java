package com.example.auctionarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

  private static final long serialVersionUID = 945267165L;

  private static final PathInits INITS = PathInits.DIRECT2;

  public static final QProduct product = new QProduct("product");

  public final QBaseEntity _super = new QBaseEntity(this);

  public final NumberPath<Long> biddingDate = createNumber(
    "biddingDate",
    Long.class
  );

  public final QCategory category;

  public final StringPath content = createString("content");

  //inherited
  public final DateTimePath<java.time.LocalDateTime> createdDate =
    _super.createdDate;

  //inherited
  public final DateTimePath<java.time.LocalDateTime> lastModifiedDate =
    _super.lastModifiedDate;

  public final QMember member;

  public final NumberPath<Long> pno = createNumber("pno", Long.class);

  public final NumberPath<Long> startPrice = createNumber(
    "startPrice",
    Long.class
  );

  public final StringPath title = createString("title");

  public QProduct(String variable) {
    this(Product.class, forVariable(variable), INITS);
  }

  public QProduct(Path<? extends Product> path) {
    this(
      path.getType(),
      path.getMetadata(),
      PathInits.getFor(path.getMetadata(), INITS)
    );
  }

  public QProduct(PathMetadata metadata) {
    this(metadata, PathInits.getFor(metadata, INITS));
  }

  public QProduct(PathMetadata metadata, PathInits inits) {
    this(Product.class, metadata, inits);
  }

  public QProduct(
    Class<? extends Product> type,
    PathMetadata metadata,
    PathInits inits
  ) {
    super(type, metadata, inits);
    this.category =
      inits.isInitialized("category")
        ? new QCategory(forProperty("category"))
        : null;
    this.member =
      inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
  }
}
