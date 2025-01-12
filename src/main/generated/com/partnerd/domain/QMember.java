package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 21413983L;

    public static final QMember member = new QMember("member1");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath belong_to_club = createString("belong_to_club");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final ListPath<CollabInquiry, QCollabInquiry> collabInquiryList = this.<CollabInquiry, QCollabInquiry>createList("collabInquiryList", CollabInquiry.class, QCollabInquiry.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath occupation_of_interest = createString("occupation_of_interest");

    public final StringPath password = createString("password");

    public final StringPath profile_url = createString("profile_url");

    public final EnumPath<com.partnerd.domain.enums.SocialType> socialType = createEnum("socialType", com.partnerd.domain.enums.SocialType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

