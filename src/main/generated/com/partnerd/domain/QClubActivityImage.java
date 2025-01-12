package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubActivityImage is a Querydsl query type for ClubActivityImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubActivityImage extends EntityPathBase<ClubActivityImage> {

    private static final long serialVersionUID = -352737391L;

    public static final QClubActivityImage clubActivityImage = new QClubActivityImage("clubActivityImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubActivityImage(String variable) {
        super(ClubActivityImage.class, forVariable(variable));
    }

    public QClubActivityImage(Path<? extends ClubActivityImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubActivityImage(PathMetadata metadata) {
        super(ClubActivityImage.class, metadata);
    }

}

