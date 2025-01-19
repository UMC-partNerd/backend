package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommunityImage is a Querydsl query type for CommunityImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityImage extends EntityPathBase<CommunityImage> {

    private static final long serialVersionUID = -233196617L;

    public static final QCommunityImage communityImage = new QCommunityImage("communityImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCommunityImage(String variable) {
        super(CommunityImage.class, forVariable(variable));
    }

    public QCommunityImage(Path<? extends CommunityImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommunityImage(PathMetadata metadata) {
        super(CommunityImage.class, metadata);
    }

}

