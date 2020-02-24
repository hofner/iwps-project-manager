package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkBreakdownStructureStatusEvent is a Querydsl query type for WorkBreakdownStructureStatusEvent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkBreakdownStructureStatusEvent extends EntityPathBase<WorkBreakdownStructureStatusEvent> {

    private static final long serialVersionUID = 1563956025L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkBreakdownStructureStatusEvent workBreakdownStructureStatusEvent = new QWorkBreakdownStructureStatusEvent("workBreakdownStructureStatusEvent");

    public final StringPath details = createString("details");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath newStatus = createString("newStatus");

    public final StringPath oldStatus = createString("oldStatus");

    public final DateTimePath<java.util.Date> triggeredAt = createDateTime("triggeredAt", java.util.Date.class);

    public final StringPath triggeredBy = createString("triggeredBy");

    public final QWorkBreakdownStructure workBreakdownStructure;

    public QWorkBreakdownStructureStatusEvent(String variable) {
        this(WorkBreakdownStructureStatusEvent.class, forVariable(variable), INITS);
    }

    public QWorkBreakdownStructureStatusEvent(Path<? extends WorkBreakdownStructureStatusEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkBreakdownStructureStatusEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkBreakdownStructureStatusEvent(PathMetadata metadata, PathInits inits) {
        this(WorkBreakdownStructureStatusEvent.class, metadata, inits);
    }

    public QWorkBreakdownStructureStatusEvent(Class<? extends WorkBreakdownStructureStatusEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workBreakdownStructure = inits.isInitialized("workBreakdownStructure") ? new QWorkBreakdownStructure(forProperty("workBreakdownStructure"), inits.get("workBreakdownStructure")) : null;
    }

}

