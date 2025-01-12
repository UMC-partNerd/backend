package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonalLink is a Querydsl query type for PersonalLink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonalLink extends EntityPathBase<PersonalLink> {

    private static final long serialVersionUID = 1585789023L;

    public static final QPersonalLink personalLink = new QPersonalLink("personalLink");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link_url = createString("link_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPersonalLink(String variable) {
        super(PersonalLink.class, forVariable(variable));
    }

    public QPersonalLink(Path<? extends PersonalLink> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonalLink(PathMetadata metadata) {
        super(PersonalLink.class, metadata);
    }

}

