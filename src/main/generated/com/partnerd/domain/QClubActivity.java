package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubActivity is a Querydsl query type for ClubActivity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubActivity extends EntityPathBase<ClubActivity> {

    private static final long serialVersionUID = 1091302378L;

    public static final QClubActivity clubActivity = new QClubActivity("clubActivity");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubActivity(String variable) {
        super(ClubActivity.class, forVariable(variable));
    }

    public QClubActivity(Path<? extends ClubActivity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubActivity(PathMetadata metadata) {
        super(ClubActivity.class, metadata);
    }

}

