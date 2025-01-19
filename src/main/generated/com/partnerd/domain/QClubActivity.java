package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubActivity is a Querydsl query type for ClubActivity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubActivity extends EntityPathBase<ClubActivity> {

    private static final long serialVersionUID = 1091302378L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubActivity clubActivity = new QClubActivity("clubActivity");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final QClub club;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubActivity(String variable) {
        this(ClubActivity.class, forVariable(variable), INITS);
    }

    public QClubActivity(Path<? extends ClubActivity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubActivity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubActivity(PathMetadata metadata, PathInits inits) {
        this(ClubActivity.class, metadata, inits);
    }

    public QClubActivity(Class<? extends ClubActivity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

