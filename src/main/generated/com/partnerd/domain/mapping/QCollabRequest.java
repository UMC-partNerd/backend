package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollabRequest is a Querydsl query type for CollabRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollabRequest extends EntityPathBase<CollabRequest> {

    private static final long serialVersionUID = 1963375677L;

    public static final QCollabRequest collabRequest = new QCollabRequest("collabRequest");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> request_date = createDateTime("request_date", java.util.Date.class);

    public final EnumPath<com.partnerd.domain.enums.RequestStatus> status = createEnum("status", com.partnerd.domain.enums.RequestStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCollabRequest(String variable) {
        super(CollabRequest.class, forVariable(variable));
    }

    public QCollabRequest(Path<? extends CollabRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollabRequest(PathMetadata metadata) {
        super(CollabRequest.class, metadata);
    }

}

