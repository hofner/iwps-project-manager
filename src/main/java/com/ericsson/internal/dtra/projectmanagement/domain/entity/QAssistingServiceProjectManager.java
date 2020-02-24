package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAssistingServiceProjectManager is a Querydsl query type for AssistingServiceProjectManager
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAssistingServiceProjectManager extends EntityPathBase<AssistingServiceProjectManager> {

    private static final long serialVersionUID = -1461143844L;

    public static final QAssistingServiceProjectManager assistingServiceProjectManager = new QAssistingServiceProjectManager("assistingServiceProjectManager");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath fullName = createString("fullName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath signum = createString("signum");

    public QAssistingServiceProjectManager(String variable) {
        super(AssistingServiceProjectManager.class, forVariable(variable));
    }

    public QAssistingServiceProjectManager(Path<? extends AssistingServiceProjectManager> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAssistingServiceProjectManager(PathMetadata metadata) {
        super(AssistingServiceProjectManager.class, metadata);
    }

}

