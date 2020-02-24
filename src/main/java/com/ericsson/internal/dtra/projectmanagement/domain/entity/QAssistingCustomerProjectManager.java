package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAssistingCustomerProjectManager is a Querydsl query type for AssistingCustomerProjectManager
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAssistingCustomerProjectManager extends EntityPathBase<AssistingCustomerProjectManager> {

    private static final long serialVersionUID = 1505965503L;

    public static final QAssistingCustomerProjectManager assistingCustomerProjectManager = new QAssistingCustomerProjectManager("assistingCustomerProjectManager");

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

    public QAssistingCustomerProjectManager(String variable) {
        super(AssistingCustomerProjectManager.class, forVariable(variable));
    }

    public QAssistingCustomerProjectManager(Path<? extends AssistingCustomerProjectManager> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAssistingCustomerProjectManager(PathMetadata metadata) {
        super(AssistingCustomerProjectManager.class, metadata);
    }

}

