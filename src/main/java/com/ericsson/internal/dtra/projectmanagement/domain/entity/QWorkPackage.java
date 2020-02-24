package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkPackage is a Querydsl query type for WorkPackage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkPackage extends EntityPathBase<WorkPackage> {

    private static final long serialVersionUID = 360547753L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkPackage workPackage = new QWorkPackage("workPackage");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    public final StringPath code = createString("code");

    public final StringPath comments = createString("comments");

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DateTimePath<java.util.Date> dueDate = createDateTime("dueDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final StringPath networkActivity = createString("networkActivity");

    public final StringPath operationalActivity = createString("operationalActivity");

    public final StringPath purchaseOrder = createString("purchaseOrder");

    public final StringPath serviceOrder = createString("serviceOrder");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath status = createString("status");

    public final ListPath<WorkPackageStatusEvent, QWorkPackageStatusEvent> statusEvents = this.<WorkPackageStatusEvent, QWorkPackageStatusEvent>createList("statusEvents", WorkPackageStatusEvent.class, QWorkPackageStatusEvent.class, PathInits.DIRECT2);

    public final StringPath technicalInput = createString("technicalInput");

    public final StringPath technicalOutput = createString("technicalOutput");

    public final StringPath version = createString("version");

    public final QWorkBreakdownStructure workBreakdownStructure;

    public QWorkPackage(String variable) {
        this(WorkPackage.class, forVariable(variable), INITS);
    }

    public QWorkPackage(Path<? extends WorkPackage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkPackage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkPackage(PathMetadata metadata, PathInits inits) {
        this(WorkPackage.class, metadata, inits);
    }

    public QWorkPackage(Class<? extends WorkPackage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workBreakdownStructure = inits.isInitialized("workBreakdownStructure") ? new QWorkBreakdownStructure(forProperty("workBreakdownStructure"), inits.get("workBreakdownStructure")) : null;
    }

}

