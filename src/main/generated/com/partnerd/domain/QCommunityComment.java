package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommunityComment is a Querydsl query type for CommunityComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityComment extends EntityPathBase<CommunityComment> {

    private static final long serialVersionUID = -1725181509L;

    public static final QCommunityComment communityComment = new QCommunityComment("communityComment");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCommunityComment(String variable) {
        super(CommunityComment.class, forVariable(variable));
    }

    public QCommunityComment(Path<? extends CommunityComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommunityComment(PathMetadata metadata) {
        super(CommunityComment.class, metadata);
    }

}

