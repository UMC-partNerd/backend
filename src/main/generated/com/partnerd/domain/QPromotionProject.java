package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionProject is a Querydsl query type for PromotionProject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionProject extends EntityPathBase<PromotionProject> {

    private static final long serialVersionUID = -2030338949L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionProject promotionProject = new QPromotionProject("promotionProject");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final QMember member;

    public final StringPath profile_img_url = createString("profile_img_url");

    public final ListPath<PromotionProjectComment, QPromotionProjectComment> promotionProjectCommentList = this.<PromotionProjectComment, QPromotionProjectComment>createList("promotionProjectCommentList", PromotionProjectComment.class, QPromotionProjectComment.class, PathInits.DIRECT2);

    public final ListPath<PromotionProjectImage, QPromotionProjectImage> promotionProjectImageList = this.<PromotionProjectImage, QPromotionProjectImage>createList("promotionProjectImageList", PromotionProjectImage.class, QPromotionProjectImage.class, PathInits.DIRECT2);

    public final ListPath<com.partnerd.domain.mapping.PromotionProjectMember, com.partnerd.domain.mapping.QPromotionProjectMember> promotionProjectMemberList = this.<com.partnerd.domain.mapping.PromotionProjectMember, com.partnerd.domain.mapping.QPromotionProjectMember>createList("promotionProjectMemberList", com.partnerd.domain.mapping.PromotionProjectMember.class, com.partnerd.domain.mapping.QPromotionProjectMember.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> vote = createNumber("vote", Long.class);

    public QPromotionProject(String variable) {
        this(PromotionProject.class, forVariable(variable), INITS);
    }

    public QPromotionProject(Path<? extends PromotionProject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionProject(PathMetadata metadata, PathInits inits) {
        this(PromotionProject.class, metadata, inits);
    }

    public QPromotionProject(Class<? extends PromotionProject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

