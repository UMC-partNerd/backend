package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectComment is a Querydsl query type for ProjectComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectComment extends EntityPathBase<ProjectComment> {

    private static final long serialVersionUID = 714726859L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectComment projectComment = new QProjectComment("projectComment");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QProject project;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectComment(String variable) {
        this(ProjectComment.class, forVariable(variable), INITS);
    }

    public QProjectComment(Path<? extends ProjectComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectComment(PathMetadata metadata, PathInits inits) {
        this(ProjectComment.class, metadata, inits);
    }

    public QProjectComment(Class<? extends ProjectComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

