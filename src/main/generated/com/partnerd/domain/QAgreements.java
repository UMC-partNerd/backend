package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAgreements is a Querydsl query type for Agreements
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgreements extends EntityPathBase<Agreements> {

    private static final long serialVersionUID = -533021970L;

    public static final QAgreements agreements = new QAgreements("agreements");

    public final com.partnerd.domain.common.QBaseEntity _super = new com.partnerd.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath is_adult = createBoolean("is_adult");

    public final BooleanPath marketing_consent = createBoolean("marketing_consent");

    public final BooleanPath marketing_notify = createBoolean("marketing_notify");

    public final BooleanPath optional_info_usage = createBoolean("optional_info_usage");

    public final BooleanPath personal_info_usage = createBoolean("personal_info_usage");

    public final BooleanPath terms_of_services = createBoolean("terms_of_services");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAgreements(String variable) {
        super(Agreements.class, forVariable(variable));
    }

    public QAgreements(Path<? extends Agreements> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgreements(PathMetadata metadata) {
        super(Agreements.class, metadata);
    }

}

