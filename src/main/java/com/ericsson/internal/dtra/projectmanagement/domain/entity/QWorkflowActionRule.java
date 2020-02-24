package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWorkflowActionRule is a Querydsl query type for WorkflowActionRule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkflowActionRule extends EntityPathBase<WorkflowActionRule> {

    private static final long serialVersionUID = 1972721469L;

    public static final QWorkflowActionRule workflowActionRule = new QWorkflowActionRule("workflowActionRule");

    public final StringPath action = createString("action");

    public final StringPath currentStatus = createString("currentStatus");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath level = createString("level");

    public final StringPath nextStatus = createString("nextStatus");

    public final StringPath normalizedPermission = createString("normalizedPermission");

    public final StringPath permission = createString("permission");

    public QWorkflowActionRule(String variable) {
        super(WorkflowActionRule.class, forVariable(variable));
    }

    public QWorkflowActionRule(Path<? extends WorkflowActionRule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkflowActionRule(PathMetadata metadata) {
        super(WorkflowActionRule.class, metadata);
    }

}

