package com.partnerd.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectMember is a Querydsl query type for ProjectMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectMember extends EntityPathBase<ProjectMember> {

    private static final long serialVersionUID = 348382606L;

    public static final QProjectMember projectMember = new QProjectMember("projectMember");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.partnerd.domain.enums.ProjectMemberRole> projectMemberRole = createEnum("projectMemberRole", com.partnerd.domain.enums.ProjectMemberRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProjectMember(String variable) {
        super(ProjectMember.class, forVariable(variable));
    }

    public QProjectMember(Path<? extends ProjectMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectMember(PathMetadata metadata) {
        super(ProjectMember.class, metadata);
    }

}

