package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectImage is a Querydsl query type for ProjectImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectImage extends EntityPathBase<ProjectImage> {

    private static final long serialVersionUID = -1531214905L;

    public static final QProjectImage projectImage = new QProjectImage("projectImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectImage(String variable) {
        super(ProjectImage.class, forVariable(variable));
    }

    public QProjectImage(Path<? extends ProjectImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectImage(PathMetadata metadata) {
        super(ProjectImage.class, metadata);
    }

}

