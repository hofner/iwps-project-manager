package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGlobalServiceCenter is a Querydsl query type for GlobalServiceCenter
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGlobalServiceCenter extends EntityPathBase<GlobalServiceCenter> {

    private static final long serialVersionUID = 1277936187L;

    public static final QGlobalServiceCenter globalServiceCenter = new QGlobalServiceCenter("globalServiceCenter");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public QGlobalServiceCenter(String variable) {
        super(GlobalServiceCenter.class, forVariable(variable));
    }

    public QGlobalServiceCenter(Path<? extends GlobalServiceCenter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGlobalServiceCenter(PathMetadata metadata) {
        super(GlobalServiceCenter.class, metadata);
    }

}

