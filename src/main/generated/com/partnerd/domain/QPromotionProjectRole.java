package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotionProjectRole is a Querydsl query type for PromotionProjectRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectRole extends EntityPathBase<PromotionProjectRole> {

    private static final long serialVersionUID = 1808382865L;

    public static final QPromotionProjectRole promotionProjectRole = new QPromotionProjectRole("promotionProjectRole");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectRole(String variable) {
        super(PromotionProjectRole.class, forVariable(variable));
    }

    public QPromotionProjectRole(Path<? extends PromotionProjectRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProjectRole(PathMetadata metadata) {
        super(PromotionProjectRole.class, metadata);
    }

}

