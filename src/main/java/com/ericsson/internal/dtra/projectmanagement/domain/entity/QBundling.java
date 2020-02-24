package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBundling is a Querydsl query type for Bundling
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBundling extends EntityPathBase<Bundling> {

    private static final long serialVersionUID = -387921013L;

    public static final QBundling bundling = new QBundling("bundling");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public QBundling(String variable) {
        super(Bundling.class, forVariable(variable));
    }

    public QBundling(Path<? extends Bundling> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBundling(PathMetadata metadata) {
        super(Bundling.class, metadata);
    }

}

