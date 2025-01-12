package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubMembershipRequest is a Querydsl query type for ClubMembershipRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMembershipRequest extends EntityPathBase<ClubMembershipRequest> {

    private static final long serialVersionUID = -1082887362L;

    public static final QClubMembershipRequest clubMembershipRequest = new QClubMembershipRequest("clubMembershipRequest");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.util.Date> decision_at = createDateTime("decision_at", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> request_at = createDateTime("request_at", java.util.Date.class);

    public final EnumPath<com.partnerd.domain.enums.RequestStatus> status = createEnum("status", com.partnerd.domain.enums.RequestStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubMembershipRequest(String variable) {
        super(ClubMembershipRequest.class, forVariable(variable));
    }

    public QClubMembershipRequest(Path<? extends ClubMembershipRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubMembershipRequest(PathMetadata metadata) {
        super(ClubMembershipRequest.class, metadata);
    }

}

