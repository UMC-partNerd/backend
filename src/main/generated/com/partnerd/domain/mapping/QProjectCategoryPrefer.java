package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectCategoryPrefer is a Querydsl query type for ProjectCategoryPrefer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectCategoryPrefer extends EntityPathBase<ProjectCategoryPrefer> {

    private static final long serialVersionUID = 2091862946L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectCategoryPrefer projectCategoryPrefer = new QProjectCategoryPrefer("projectCategoryPrefer");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.partnerd.domain.QProject project;

    public final com.partnerd.domain.QProjectCategory projectCategory;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectCategoryPrefer(String variable) {
        this(ProjectCategoryPrefer.class, forVariable(variable), INITS);
    }

    public QProjectCategoryPrefer(Path<? extends ProjectCategoryPrefer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectCategoryPrefer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectCategoryPrefer(PathMetadata metadata, PathInits inits) {
        this(ProjectCategoryPrefer.class, metadata, inits);
    }

    public QProjectCategoryPrefer(Class<? extends ProjectCategoryPrefer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new com.partnerd.domain.QProject(forProperty("project"), inits.get("project")) : null;
        this.projectCategory = inits.isInitialized("projectCategory") ? new com.partnerd.domain.QProjectCategory(forProperty("projectCategory")) : null;
    }

}

