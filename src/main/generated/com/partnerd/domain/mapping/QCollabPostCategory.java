package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCollabPostCategory is a Querydsl query type for CollabPostCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabPostCategory extends EntityPathBase<CollabPostCategory> {

    private static final long serialVersionUID = -1922552624L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCollabPostCategory collabPostCategory = new QCollabPostCategory("collabPostCategory");

    public final com.partnerd.domain.QCategory category;

    public final com.partnerd.domain.QCollabPost collabPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCollabPostCategory(String variable) {
        this(CollabPostCategory.class, forVariable(variable), INITS);
    }

    public QCollabPostCategory(Path<? extends CollabPostCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCollabPostCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCollabPostCategory(PathMetadata metadata, PathInits inits) {
        this(CollabPostCategory.class, metadata, inits);
    }

    public QCollabPostCategory(Class<? extends CollabPostCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.partnerd.domain.QCategory(forProperty("category")) : null;
        this.collabPost = inits.isInitialized("collabPost") ? new com.partnerd.domain.QCollabPost(forProperty("collabPost"), inits.get("collabPost")) : null;
    }

}

