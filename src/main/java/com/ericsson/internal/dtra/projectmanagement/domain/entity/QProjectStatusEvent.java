package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectStatusEvent is a Querydsl query type for ProjectStatusEvent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProjectStatusEvent extends EntityPathBase<ProjectStatusEvent> {

    private static final long serialVersionUID = -60816197L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectStatusEvent projectStatusEvent = new QProjectStatusEvent("projectStatusEvent");

    public final StringPath details = createString("details");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath newStatus = createString("newStatus");

    public final StringPath oldStatus = createString("oldStatus");

    public final QProject project;

    public final DateTimePath<java.util.Date> triggeredAt = createDateTime("triggeredAt", java.util.Date.class);

    public final StringPath triggeredBy = createString("triggeredBy");

    public QProjectStatusEvent(String variable) {
        this(ProjectStatusEvent.class, forVariable(variable), INITS);
    }

    public QProjectStatusEvent(Path<? extends ProjectStatusEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectStatusEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectStatusEvent(PathMetadata metadata, PathInits inits) {
        this(ProjectStatusEvent.class, metadata, inits);
    }

    public QProjectStatusEvent(Class<? extends ProjectStatusEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

