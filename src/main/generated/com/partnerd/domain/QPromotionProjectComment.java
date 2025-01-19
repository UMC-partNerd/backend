package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotionProjectComment is a Querydsl query type for PromotionProjectComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectComment extends EntityPathBase<PromotionProjectComment> {

    private static final long serialVersionUID = 1332746596L;

    public static final QPromotionProjectComment promotionProjectComment = new QPromotionProjectComment("promotionProjectComment");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectComment(String variable) {
        super(PromotionProjectComment.class, forVariable(variable));
    }

    public QPromotionProjectComment(Path<? extends PromotionProjectComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProjectComment(PathMetadata metadata) {
        super(PromotionProjectComment.class, metadata);
    }

}

