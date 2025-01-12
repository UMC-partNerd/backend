package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -594358796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath current_progress = createString("current_progress");

    public final StringPath description = createString("description");

    public final StringPath design_stack = createString("design_stack");

    public final StringPath dev_stack = createString("dev_stack");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final QMember member;

    public final StringPath part = createString("part");

    public final StringPath pm_stack = createString("pm_stack");

    public final StringPath profile_img_url = createString("profile_img_url");

    public final ListPath<com.partnerd.domain.mapping.ProjectCategoryPrefer, com.partnerd.domain.mapping.QProjectCategoryPrefer> projectCategoryPreferList = this.<com.partnerd.domain.mapping.ProjectCategoryPrefer, com.partnerd.domain.mapping.QProjectCategoryPrefer>createList("projectCategoryPreferList", com.partnerd.domain.mapping.ProjectCategoryPrefer.class, com.partnerd.domain.mapping.QProjectCategoryPrefer.class, PathInits.DIRECT2);

    public final ListPath<ProjectComment, QProjectComment> projectCommentList = this.<ProjectComment, QProjectComment>createList("projectCommentList", ProjectComment.class, QProjectComment.class, PathInits.DIRECT2);

    public final ListPath<ProjectImage, QProjectImage> projectImageList = this.<ProjectImage, QProjectImage>createList("projectImageList", ProjectImage.class, QProjectImage.class, PathInits.DIRECT2);

    public final ListPath<com.partnerd.domain.mapping.ProjectMember, com.partnerd.domain.mapping.QProjectMember> projectMemberList = this.<com.partnerd.domain.mapping.ProjectMember, com.partnerd.domain.mapping.QProjectMember>createList("projectMemberList", com.partnerd.domain.mapping.ProjectMember.class, com.partnerd.domain.mapping.QProjectMember.class, PathInits.DIRECT2);

    public final EnumPath<com.partnerd.domain.enums.ProjectStatus> projectStatus = createEnum("projectStatus", com.partnerd.domain.enums.ProjectStatus.class);

    public final StringPath skill = createString("skill");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

