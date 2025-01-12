package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionProjectImage is a Querydsl query type for PromotionProjectImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectImage extends EntityPathBase<PromotionProjectImage> {

    private static final long serialVersionUID = 216912288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionProjectImage promotionProjectImage = new QPromotionProjectImage("promotionProjectImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    public final QPromotionProject promotionProject;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectImage(String variable) {
        this(PromotionProjectImage.class, forVariable(variable), INITS);
    }

    public QPromotionProjectImage(Path<? extends PromotionProjectImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionProjectImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionProjectImage(PathMetadata metadata, PathInits inits) {
        this(PromotionProjectImage.class, metadata, inits);
    }

    public QPromotionProjectImage(Class<? extends PromotionProjectImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.promotionProject = inits.isInitialized("promotionProject") ? new QPromotionProject(forProperty("promotionProject"), inits.get("promotionProject")) : null;
    }

}

