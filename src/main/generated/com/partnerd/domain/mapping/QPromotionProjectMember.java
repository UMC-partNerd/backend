package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPromotionProjectMember is a Querydsl query type for PromotionProjectMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProjectMember extends EntityPathBase<PromotionProjectMember> {

    private static final long serialVersionUID = 1000627989L;

    public static final QPromotionProjectMember promotionProjectMember = new QPromotionProjectMember("promotionProjectMember");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.partnerd.domain.enums.ProjectMemberRole> projectMemberRole = createEnum("projectMemberRole", com.partnerd.domain.enums.ProjectMemberRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionProjectMember(String variable) {
        super(PromotionProjectMember.class, forVariable(variable));
    }

    public QPromotionProjectMember(Path<? extends PromotionProjectMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProjectMember(PathMetadata metadata) {
        super(PromotionProjectMember.class, metadata);
    }

}

