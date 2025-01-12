package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreements is a Querydsl query type for Agreements
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgreements extends EntityPathBase<Agreements> {

    private static final long serialVersionUID = -533021970L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreements agreements = new QAgreements("agreements");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_adult = createBoolean("is_adult");

    public final BooleanPath marketing_consent = createBoolean("marketing_consent");

    public final BooleanPath marketing_notify = createBoolean("marketing_notify");

    public final QMember member;

    public final BooleanPath optional_info_usage = createBoolean("optional_info_usage");

    public final BooleanPath personal_info_usage = createBoolean("personal_info_usage");

    public final BooleanPath terms_of_services = createBoolean("terms_of_services");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAgreements(String variable) {
        this(Agreements.class, forVariable(variable), INITS);
    }

    public QAgreements(Path<? extends Agreements> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreements(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreements(PathMetadata metadata, PathInits inits) {
        this(Agreements.class, metadata, inits);
    }

    public QAgreements(Class<? extends Agreements> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

