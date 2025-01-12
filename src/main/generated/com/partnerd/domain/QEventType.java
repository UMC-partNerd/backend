package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventType is a Querydsl query type for EventType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventType extends EntityPathBase<EventType> {

    private static final long serialVersionUID = 978112783L;

    public static final QEventType eventType = new QEventType("eventType");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final ListPath<CollabPost, QCollabPost> collabPostList = this.<CollabPost, QCollabPost>createList("collabPostList", CollabPost.class, QCollabPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEventType(String variable) {
        super(EventType.class, forVariable(variable));
    }

    public QEventType(Path<? extends EventType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventType(PathMetadata metadata) {
        super(EventType.class, metadata);
    }

}

