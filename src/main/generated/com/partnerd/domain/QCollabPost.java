package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCollabPost is a Querydsl query type for CollabPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabPost extends EntityPathBase<CollabPost> {

    private static final long serialVersionUID = 1719515538L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCollabPost collabPost = new QCollabPost("collabPost");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final DateTimePath<java.util.Date> close_date = createDateTime("close_date", java.util.Date.class);

    public final com.partnerd.domain.mapping.QClubMember clubMember;

    public final StringPath collab_target = createString("collab_target");

    public final ListPath<com.partnerd.domain.mapping.CollabPostCategory, com.partnerd.domain.mapping.QCollabPostCategory> collabPostCategoryList = this.<com.partnerd.domain.mapping.CollabPostCategory, com.partnerd.domain.mapping.QCollabPostCategory>createList("collabPostCategoryList", com.partnerd.domain.mapping.CollabPostCategory.class, com.partnerd.domain.mapping.QCollabPostCategory.class, PathInits.DIRECT2);

    public final ListPath<ContactMethod, QContactMethod> contactMethodList = this.<ContactMethod, QContactMethod>createList("contactMethodList", ContactMethod.class, QContactMethod.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> end_date = createDateTime("end_date", java.util.Date.class);

    public final NumberPath<Integer> event_mode = createNumber("event_mode", Integer.class);

    public final QEventType eventType;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final DateTimePath<java.util.Date> open_date = createDateTime("open_date", java.util.Date.class);

    public final DateTimePath<java.util.Date> start_date = createDateTime("start_date", java.util.Date.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCollabPost(String variable) {
        this(CollabPost.class, forVariable(variable), INITS);
    }

    public QCollabPost(Path<? extends CollabPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCollabPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCollabPost(PathMetadata metadata, PathInits inits) {
        this(CollabPost.class, metadata, inits);
    }

    public QCollabPost(Class<? extends CollabPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clubMember = inits.isInitialized("clubMember") ? new com.partnerd.domain.mapping.QClubMember(forProperty("clubMember"), inits.get("clubMember")) : null;
        this.eventType = inits.isInitialized("eventType") ? new QEventType(forProperty("eventType")) : null;
    }

}

