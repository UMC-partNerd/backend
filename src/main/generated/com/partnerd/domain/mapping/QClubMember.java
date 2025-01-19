package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMember is a Querydsl query type for ClubMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMember extends EntityPathBase<ClubMember> {

    private static final long serialVersionUID = 1415064149L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMember clubMember = new QClubMember("clubMember");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final com.partnerd.domain.QClub club;

    public final ListPath<com.partnerd.domain.CollabPost, com.partnerd.domain.QCollabPost> collabPostList = this.<com.partnerd.domain.CollabPost, com.partnerd.domain.QCollabPost>createList("collabPostList", com.partnerd.domain.CollabPost.class, com.partnerd.domain.QCollabPost.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> joined_date = createDateTime("joined_date", java.util.Date.class);

    public final com.partnerd.domain.QMember member;

    public final EnumPath<com.partnerd.domain.enums.ClubMemberRole> role = createEnum("role", com.partnerd.domain.enums.ClubMemberRole.class);

    public final EnumPath<com.partnerd.domain.enums.ActiveType> status = createEnum("status", com.partnerd.domain.enums.ActiveType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubMember(String variable) {
        this(ClubMember.class, forVariable(variable), INITS);
    }

    public QClubMember(Path<? extends ClubMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMember(PathMetadata metadata, PathInits inits) {
        this(ClubMember.class, metadata, inits);
    }

    public QClubMember(Class<? extends ClubMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new com.partnerd.domain.QClub(forProperty("club"), inits.get("club")) : null;
        this.member = inits.isInitialized("member") ? new com.partnerd.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

