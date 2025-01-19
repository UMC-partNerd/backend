package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionProjectComment is a Querydsl query type for PromotionProjectComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectComment extends EntityPathBase<PromotionProjectComment> {

    private static final long serialVersionUID = 1332746596L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionProjectComment promotionProjectComment = new QPromotionProjectComment("promotionProjectComment");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QPromotionProject promotionProject;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectComment(String variable) {
        this(PromotionProjectComment.class, forVariable(variable), INITS);
    }

    public QPromotionProjectComment(Path<? extends PromotionProjectComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionProjectComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionProjectComment(PathMetadata metadata, PathInits inits) {
        this(PromotionProjectComment.class, metadata, inits);
    }

    public QPromotionProjectComment(Class<? extends PromotionProjectComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.promotionProject = inits.isInitialized("promotionProject") ? new QPromotionProject(forProperty("promotionProject"), inits.get("promotionProject")) : null;
    }

}

