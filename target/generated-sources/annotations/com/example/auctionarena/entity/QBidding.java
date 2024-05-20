package com.example.auctionarena.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBidding is a Querydsl query type for Bidding
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidding extends EntityPathBase<Bidding> {

    private static final long serialVersionUID = 1137285225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBidding bidding = new QBidding("bidding");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> biddingPrice = createNumber("biddingPrice", Long.class);

    public final NumberPath<Long> bno = createNumber("bno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final QProduct product;

    public QBidding(String variable) {
        this(Bidding.class, forVariable(variable), INITS);
    }

    public QBidding(Path<? extends Bidding> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBidding(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBidding(PathMetadata metadata, PathInits inits) {
        this(Bidding.class, metadata, inits);
    }

    public QBidding(Class<? extends Bidding> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

