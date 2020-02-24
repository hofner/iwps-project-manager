package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWorkflowEditRule is a Querydsl query type for WorkflowEditRule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkflowEditRule extends EntityPathBase<WorkflowEditRule> {

    private static final long serialVersionUID = 1834515409L;

    public static final QWorkflowEditRule workflowEditRule = new QWorkflowEditRule("workflowEditRule");

    public final StringPath currentStatus = createString("currentStatus");

    public final StringPath field = createString("field");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath level = createString("level");

    public final StringPath normalizedPermission = createString("normalizedPermission");

    public final StringPath permission = createString("permission");

    public QWorkflowEditRule(String variable) {
        super(WorkflowEditRule.class, forVariable(variable));
    }

    public QWorkflowEditRule(Path<? extends WorkflowEditRule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkflowEditRule(PathMetadata metadata) {
        super(WorkflowEditRule.class, metadata);
    }

}

