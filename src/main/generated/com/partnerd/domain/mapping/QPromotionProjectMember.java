package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionProjectMember is a Querydsl query type for PromotionProjectMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectMember extends EntityPathBase<PromotionProjectMember> {

    private static final long serialVersionUID = 1000627989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionProjectMember promotionProjectMember = new QPromotionProjectMember("promotionProjectMember");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.partnerd.domain.QMember member;

    public final com.partnerd.domain.QPromotionProject promotionProject;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectMember(String variable) {
        this(PromotionProjectMember.class, forVariable(variable), INITS);
    }

    public QPromotionProjectMember(Path<? extends PromotionProjectMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionProjectMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionProjectMember(PathMetadata metadata, PathInits inits) {
        this(PromotionProjectMember.class, metadata, inits);
    }

    public QPromotionProjectMember(Class<? extends PromotionProjectMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.partnerd.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.promotionProject = inits.isInitialized("promotionProject") ? new com.partnerd.domain.QPromotionProject(forProperty("promotionProject"), inits.get("promotionProject")) : null;
    }

}

