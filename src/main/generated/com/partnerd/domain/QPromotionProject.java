package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotionProject is a Querydsl query type for PromotionProject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProject extends EntityPathBase<PromotionProject> {

    private static final long serialVersionUID = -2030338949L;

    public static final QPromotionProject promotionProject = new QPromotionProject("promotionProject");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final StringPath profile_img_url = createString("profile_img_url");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> vote = createNumber("vote", Long.class);

    public QPromotionProject(String variable) {
        super(PromotionProject.class, forVariable(variable));
    }

    public QPromotionProject(Path<? extends PromotionProject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProject(PathMetadata metadata) {
        super(PromotionProject.class, metadata);
    }

}

