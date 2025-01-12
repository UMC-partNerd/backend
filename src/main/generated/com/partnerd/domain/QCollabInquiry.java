package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollabInquiry is a Querydsl query type for CollabInquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabInquiry extends EntityPathBase<CollabInquiry> {

    private static final long serialVersionUID = -1935447147L;

    public static final QCollabInquiry collabInquiry = new QCollabInquiry("collabInquiry");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_secret = createBoolean("is_secret");

    public final EnumPath<com.partnerd.domain.enums.CollabInquiryStatus> status = createEnum("status", com.partnerd.domain.enums.CollabInquiryStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCollabInquiry(String variable) {
        super(CollabInquiry.class, forVariable(variable));
    }

    public QCollabInquiry(Path<? extends CollabInquiry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollabInquiry(PathMetadata metadata) {
        super(CollabInquiry.class, metadata);
    }

}

