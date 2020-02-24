package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkPackageStatusEvent is a Querydsl query type for WorkPackageStatusEvent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkPackageStatusEvent extends EntityPathBase<WorkPackageStatusEvent> {

    private static final long serialVersionUID = 376506463L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkPackageStatusEvent workPackageStatusEvent = new QWorkPackageStatusEvent("workPackageStatusEvent");

    public final StringPath details = createString("details");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath newStatus = createString("newStatus");

    public final StringPath oldStatus = createString("oldStatus");

    public final DateTimePath<java.util.Date> triggeredAt = createDateTime("triggeredAt", java.util.Date.class);

    public final StringPath triggeredBy = createString("triggeredBy");

    public final QWorkPackage workPackage;

    public QWorkPackageStatusEvent(String variable) {
        this(WorkPackageStatusEvent.class, forVariable(variable), INITS);
    }

    public QWorkPackageStatusEvent(Path<? extends WorkPackageStatusEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkPackageStatusEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkPackageStatusEvent(PathMetadata metadata, PathInits inits) {
        this(WorkPackageStatusEvent.class, metadata, inits);
    }

    public QWorkPackageStatusEvent(Class<? extends WorkPackageStatusEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workPackage = inits.isInitialized("workPackage") ? new QWorkPackage(forProperty("workPackage"), inits.get("workPackage")) : null;
    }

}

