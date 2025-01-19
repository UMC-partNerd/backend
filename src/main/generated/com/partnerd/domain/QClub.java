package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = 174032827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClub club = new QClub("club");

    public final ListPath<ClubActivity, QClubActivity> activities = this.<ClubActivity, QClubActivity>createList("activities", ClubActivity.class, QClubActivity.class, PathInits.DIRECT2);

    public final QCategory category;

    public final ListPath<com.partnerd.domain.mapping.ClubMember, com.partnerd.domain.mapping.QClubMember> clubMembers = this.<com.partnerd.domain.mapping.ClubMember, com.partnerd.domain.mapping.QClubMember>createList("clubMembers", com.partnerd.domain.mapping.ClubMember.class, com.partnerd.domain.mapping.QClubMember.class, PathInits.DIRECT2);

    public final ListPath<ContactMethod, QContactMethod> contactMethodList = this.<ContactMethod, QContactMethod>createList("contactMethodList", ContactMethod.class, QContactMethod.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final StringPath name = createString("name");

    public final StringPath profile = createString("profile");

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QClub(String variable) {
        this(Club.class, forVariable(variable), INITS);
    }

    public QClub(Path<? extends Club> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClub(PathMetadata metadata, PathInits inits) {
        this(Club.class, metadata, inits);
    }

    public QClub(Class<? extends Club> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

