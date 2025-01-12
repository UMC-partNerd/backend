package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotionProjectImage is a Querydsl query type for PromotionProjectImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectImage extends EntityPathBase<PromotionProjectImage> {

    private static final long serialVersionUID = 216912288L;

    public static final QPromotionProjectImage promotionProjectImage = new QPromotionProjectImage("promotionProjectImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectImage(String variable) {
        super(PromotionProjectImage.class, forVariable(variable));
    }

    public QPromotionProjectImage(Path<? extends PromotionProjectImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProjectImage(PathMetadata metadata) {
        super(PromotionProjectImage.class, metadata);
    }

}

