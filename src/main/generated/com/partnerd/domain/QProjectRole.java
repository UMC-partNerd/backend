package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectRole is a Querydsl query type for ProjectRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectRole extends EntityPathBase<ProjectRole> {

    private static final long serialVersionUID = -1711691638L;

    public static final QProjectRole projectRole = new QProjectRole("projectRole");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectRole(String variable) {
        super(ProjectRole.class, forVariable(variable));
    }

    public QProjectRole(Path<? extends ProjectRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectRole(PathMetadata metadata) {
        super(ProjectRole.class, metadata);
    }

}

