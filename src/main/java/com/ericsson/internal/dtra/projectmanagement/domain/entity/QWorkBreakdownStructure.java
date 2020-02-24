package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkBreakdownStructure is a Querydsl query type for WorkBreakdownStructure
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkBreakdownStructure extends EntityPathBase<WorkBreakdownStructure> {

    private static final long serialVersionUID = -948912369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkBreakdownStructure workBreakdownStructure = new QWorkBreakdownStructure("workBreakdownStructure");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    public final StringPath comments = createString("comments");

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final DateTimePath<java.util.Date> dueDate = createDateTime("dueDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath label = createString("label");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final StringPath networkActivity = createString("networkActivity");

    public final StringPath operationalActivity = createString("operationalActivity");

    public final QProject project;

    public final StringPath purchaseOrder = createString("purchaseOrder");

    public final StringPath site = createString("site");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath status = createString("status");

    public final ListPath<WorkBreakdownStructureStatusEvent, QWorkBreakdownStructureStatusEvent> statusEvents = this.<WorkBreakdownStructureStatusEvent, QWorkBreakdownStructureStatusEvent>createList("statusEvents", WorkBreakdownStructureStatusEvent.class, QWorkBreakdownStructureStatusEvent.class, PathInits.DIRECT2);

    public final NumberPath<Long> subNetwork = createNumber("subNetwork", Long.class);

    public final StringPath technicalInput = createString("technicalInput");

    public final StringPath technicalOutput = createString("technicalOutput");

    public final ListPath<WorkPackage, QWorkPackage> workPackages = this.<WorkPackage, QWorkPackage>createList("workPackages", WorkPackage.class, QWorkPackage.class, PathInits.DIRECT2);

    public QWorkBreakdownStructure(String variable) {
        this(WorkBreakdownStructure.class, forVariable(variable), INITS);
    }

    public QWorkBreakdownStructure(Path<? extends WorkBreakdownStructure> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkBreakdownStructure(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkBreakdownStructure(PathMetadata metadata, PathInits inits) {
        this(WorkBreakdownStructure.class, metadata, inits);
    }

    public QWorkBreakdownStructure(Class<? extends WorkBreakdownStructure> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

