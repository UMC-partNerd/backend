package com.partnerd.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContactMethod is a Querydsl query type for ContactMethod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContactMethod extends EntityPathBase<ContactMethod> {

    private static final long serialVersionUID = 1783364540L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContactMethod contactMethod = new QContactMethod("contactMethod");

    public final QCollabPost collabPost;

    public final StringPath contact_type = createString("contact_type");

    public final StringPath contact_url = createString("contact_url");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QContactMethod(String variable) {
        this(ContactMethod.class, forVariable(variable), INITS);
    }

    public QContactMethod(Path<? extends ContactMethod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContactMethod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContactMethod(PathMetadata metadata, PathInits inits) {
        this(ContactMethod.class, metadata, inits);
    }

    public QContactMethod(Class<? extends ContactMethod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.collabPost = inits.isInitialized("collabPost") ? new QCollabPost(forProperty("collabPost"), inits.get("collabPost")) : null;
    }

}

