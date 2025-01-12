package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectCategory is a Querydsl query type for ProjectCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectCategory extends EntityPathBase<ProjectCategory> {

    private static final long serialVersionUID = 1334622994L;

    public static final QProjectCategory projectCategory = new QProjectCategory("projectCategory");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.partnerd.domain.mapping.ProjectCategoryPrefer, com.partnerd.domain.mapping.QProjectCategoryPrefer> projectCategoryPreferList = this.<com.partnerd.domain.mapping.ProjectCategoryPrefer, com.partnerd.domain.mapping.QProjectCategoryPrefer>createList("projectCategoryPreferList", com.partnerd.domain.mapping.ProjectCategoryPrefer.class, com.partnerd.domain.mapping.QProjectCategoryPrefer.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectCategory(String variable) {
        super(ProjectCategory.class, forVariable(variable));
    }

    public QProjectCategory(Path<? extends ProjectCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectCategory(PathMetadata metadata) {
        super(ProjectCategory.class, metadata);
    }

}

