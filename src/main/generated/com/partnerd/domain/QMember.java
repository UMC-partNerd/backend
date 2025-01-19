package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 21413983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    public final QAgreements agreement;

    public final StringPath belong_to_club = createString("belong_to_club");

    public final DateTimePath<java.util.Date> birth = createDateTime("birth", java.util.Date.class);

    public final ListPath<com.partnerd.domain.mapping.ClubMember, com.partnerd.domain.mapping.QClubMember> clubMembers = this.<com.partnerd.domain.mapping.ClubMember, com.partnerd.domain.mapping.QClubMember>createList("clubMembers", com.partnerd.domain.mapping.ClubMember.class, com.partnerd.domain.mapping.QClubMember.class, PathInits.DIRECT2);

    public final ListPath<CollabInquiry, QCollabInquiry> collabInquiryList = this.<CollabInquiry, QCollabInquiry>createList("collabInquiryList", CollabInquiry.class, QCollabInquiry.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath occupation_of_interest = createString("occupation_of_interest");

    public final StringPath password = createString("password");

    public final StringPath profile_url = createString("profile_url");

    public final ListPath<ProjectComment, QProjectComment> projectCommentList = this.<ProjectComment, QProjectComment>createList("projectCommentList", ProjectComment.class, QProjectComment.class, PathInits.DIRECT2);

    public final ListPath<Project, QProject> projectList = this.<Project, QProject>createList("projectList", Project.class, QProject.class, PathInits.DIRECT2);

    public final ListPath<com.partnerd.domain.mapping.ProjectMember, com.partnerd.domain.mapping.QProjectMember> projectMemberList = this.<com.partnerd.domain.mapping.ProjectMember, com.partnerd.domain.mapping.QProjectMember>createList("projectMemberList", com.partnerd.domain.mapping.ProjectMember.class, com.partnerd.domain.mapping.QProjectMember.class, PathInits.DIRECT2);

    public final ListPath<PromotionProjectComment, QPromotionProjectComment> promotionProjectCommentList = this.<PromotionProjectComment, QPromotionProjectComment>createList("promotionProjectCommentList", PromotionProjectComment.class, QPromotionProjectComment.class, PathInits.DIRECT2);

    public final ListPath<PromotionProject, QPromotionProject> promotionProjectList = this.<PromotionProject, QPromotionProject>createList("promotionProjectList", PromotionProject.class, QPromotionProject.class, PathInits.DIRECT2);

    public final ListPath<com.partnerd.domain.mapping.PromotionProjectMember, com.partnerd.domain.mapping.QPromotionProjectMember> promotionProjectMemberList = this.<com.partnerd.domain.mapping.PromotionProjectMember, com.partnerd.domain.mapping.QPromotionProjectMember>createList("promotionProjectMemberList", com.partnerd.domain.mapping.PromotionProjectMember.class, com.partnerd.domain.mapping.QPromotionProjectMember.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.partnerd.domain.enums.SocialType> socialType = createEnum("socialType", com.partnerd.domain.enums.SocialType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agreement = inits.isInitialized("agreement") ? new QAgreements(forProperty("agreement")) : null;
    }

}

