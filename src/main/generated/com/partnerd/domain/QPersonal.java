package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonal is a Querydsl query type for Personal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonal extends EntityPathBase<Personal> {

    private static final long serialVersionUID = 196607685L;

    public static final QPersonal personal = new QPersonal("personal");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath activity_project = createString("activity_project");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath education = createString("education");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final StringPath personal_history = createString("personal_history");

    public final StringPath skill = createString("skill");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPersonal(String variable) {
        super(Personal.class, forVariable(variable));
    }

    public QPersonal(Path<? extends Personal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonal(PathMetadata metadata) {
        super(Personal.class, metadata);
    }

}

