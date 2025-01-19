package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectImage is a Querydsl query type for ProjectImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectImage extends EntityPathBase<ProjectImage> {

    private static final long serialVersionUID = -1531214905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectImage projectImage = new QProjectImage("projectImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    public final QProject project;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectImage(String variable) {
        this(ProjectImage.class, forVariable(variable), INITS);
    }

    public QProjectImage(Path<? extends ProjectImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectImage(PathMetadata metadata, PathInits inits) {
        this(ProjectImage.class, metadata, inits);
    }

    public QProjectImage(Class<? extends ProjectImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

