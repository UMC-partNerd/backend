package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCollabInquiry is a Querydsl query type for CollabInquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabInquiry extends EntityPathBase<CollabInquiry> {

    private static final long serialVersionUID = -1935447147L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCollabInquiry collabInquiry = new QCollabInquiry("collabInquiry");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_secret = createBoolean("is_secret");

    public final QMember member;

    public final EnumPath<com.partnerd.domain.enums.CollabInquiryStatus> status = createEnum("status", com.partnerd.domain.enums.CollabInquiryStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCollabInquiry(String variable) {
        this(CollabInquiry.class, forVariable(variable), INITS);
    }

    public QCollabInquiry(Path<? extends CollabInquiry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCollabInquiry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCollabInquiry(PathMetadata metadata, PathInits inits) {
        this(CollabInquiry.class, metadata, inits);
    }

    public QCollabInquiry(Class<? extends CollabInquiry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

