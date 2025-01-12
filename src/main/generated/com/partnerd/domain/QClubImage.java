package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubImage is a Querydsl query type for ClubImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubImage extends EntityPathBase<ClubImage> {

    private static final long serialVersionUID = 982437472L;

    public static final QClubImage clubImage = new QClubImage("clubImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.partnerd.domain.enums.ImageType> image_type = createEnum("image_type", com.partnerd.domain.enums.ImageType.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QClubImage(String variable) {
        super(ClubImage.class, forVariable(variable));
    }

    public QClubImage(Path<? extends ClubImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubImage(PathMetadata metadata) {
        super(ClubImage.class, metadata);
    }

}

