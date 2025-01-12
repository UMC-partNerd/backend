package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollabPost is a Querydsl query type for CollabPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabPost extends EntityPathBase<CollabPost> {

    private static final long serialVersionUID = 1719515538L;

    public static final QCollabPost collabPost = new QCollabPost("collabPost");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final DateTimePath<java.util.Date> close_date = createDateTime("close_date", java.util.Date.class);

    public final StringPath collab_target = createString("collab_target");

    public final StringPath contact_method = createString("contact_method");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> end_date = createDateTime("end_date", java.util.Date.class);

    public final NumberPath<Long> event_mode = createNumber("event_mode", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final DateTimePath<java.util.Date> open_date = createDateTime("open_date", java.util.Date.class);

    public final DateTimePath<java.util.Date> start_date = createDateTime("start_date", java.util.Date.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCollabPost(String variable) {
        super(CollabPost.class, forVariable(variable));
    }

    public QCollabPost(Path<? extends CollabPost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollabPost(PathMetadata metadata) {
        super(CollabPost.class, metadata);
    }

}

