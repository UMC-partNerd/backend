package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventImage is a Querydsl query type for EventImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventImage extends EntityPathBase<EventImage> {

    private static final long serialVersionUID = 246194726L;

    public static final QEventImage eventImage = new QEventImage("eventImage");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.partnerd.domain.enums.ImageType> image_type = createEnum("image_type", com.partnerd.domain.enums.ImageType.class);

    public final StringPath image_url = createString("image_url");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEventImage(String variable) {
        super(EventImage.class, forVariable(variable));
    }

    public QEventImage(Path<? extends EventImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventImage(PathMetadata metadata) {
        super(EventImage.class, metadata);
    }

}

