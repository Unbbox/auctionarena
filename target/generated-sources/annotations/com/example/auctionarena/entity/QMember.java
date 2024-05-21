package com.example.auctionarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import javax.annotation.processing.Generated;

/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

  private static final long serialVersionUID = 1595105260L;

  public static final QMember member = new QMember("member1");

  public final QBaseEntity _super = new QBaseEntity(this);

  public final StringPath addr = createString("addr");

  //inherited
  public final DateTimePath<java.time.LocalDateTime> createdDate =
    _super.createdDate;

  public final StringPath email = createString("email");

  //inherited
  public final DateTimePath<java.time.LocalDateTime> lastModifiedDate =
    _super.lastModifiedDate;

  public final NumberPath<Long> mid = createNumber("mid", Long.class);

  public final StringPath name = createString("name");

  public final StringPath nickname = createString("nickname");

  public final StringPath password = createString("password");

  public final StringPath phoneNumber = createString("phoneNumber");

  public final EnumPath<com.example.auctionarena.constant.MemberRole> role = createEnum(
    "role",
    com.example.auctionarena.constant.MemberRole.class
  );

  public final StringPath zonecode = createString("zonecode");

  public QMember(String variable) {
    super(Member.class, forVariable(variable));
  }

  public QMember(Path<? extends Member> path) {
    super(path.getType(), path.getMetadata());
  }

  public QMember(PathMetadata metadata) {
    super(Member.class, metadata);
  }
}
