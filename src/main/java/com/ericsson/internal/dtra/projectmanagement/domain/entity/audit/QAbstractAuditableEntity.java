package com.ericsson.internal.dtra.projectmanagement.domain.entity.audit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractAuditableEntity is a Querydsl query type for AbstractAuditableEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractAuditableEntity extends EntityPathBase<AbstractAuditableEntity> {

    private static final long serialVersionUID = 333639447L;

    public static final QAbstractAuditableEntity abstractAuditableEntity = new QAbstractAuditableEntity("abstractAuditableEntity");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.util.Date> lastModifiedAt = createDateTime("lastModifiedAt", java.util.Date.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public QAbstractAuditableEntity(String variable) {
        super(AbstractAuditableEntity.class, forVariable(variable));
    }

    public QAbstractAuditableEntity(Path<? extends AbstractAuditableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractAuditableEntity(PathMetadata metadata) {
        super(AbstractAuditableEntity.class, metadata);
    }

}

