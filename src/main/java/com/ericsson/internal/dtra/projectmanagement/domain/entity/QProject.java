package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -141514035L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    public final ListPath<AssistingCustomerProjectManager, QAssistingCustomerProjectManager> assistingCustomerProjectManagers = this.<AssistingCustomerProjectManager, QAssistingCustomerProjectManager>createList("assistingCustomerProjectManagers", AssistingCustomerProjectManager.class, QAssistingCustomerProjectManager.class, PathInits.DIRECT2);

    public final ListPath<AssistingServiceProjectManager, QAssistingServiceProjectManager> assistingServiceProjectManagers = this.<AssistingServiceProjectManager, QAssistingServiceProjectManager>createList("assistingServiceProjectManagers", AssistingServiceProjectManager.class, QAssistingServiceProjectManager.class, PathInits.DIRECT2);

    public final QBundling bundling;

    public final StringPath comment = createString("comment");

    public final QCostModel costModel;

    public final QCountry country;

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath customer = createString("customer");

    public final StringPath customerProjectManagerFullname = createString("customerProjectManagerFullname");

    public final StringPath customerProjectManagerSignum = createString("customerProjectManagerSignum");

    public final DateTimePath<java.util.Date> dueDate = createDateTime("dueDate", java.util.Date.class);

    public final StringPath externalReferenceId = createString("externalReferenceId");

    public final QGlobalServiceCenter globalServiceCenter;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final QProductArea productArea;

    public final QRegion region;

    public final StringPath sapProjectDescription = createString("sapProjectDescription");

    public final StringPath sapProjectId = createString("sapProjectId");

    public final StringPath serviceProjectManagerFullname = createString("serviceProjectManagerFullname");

    public final StringPath serviceProjectManagerSignum = createString("serviceProjectManagerSignum");

    public final NumberPath<Long> standardNetwork = createNumber("standardNetwork", Long.class);

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath status = createString("status");

    public final StringPath timezone = createString("timezone");

    public final NumberPath<Integer> workBreakdownStructureCompletionRate = createNumber("workBreakdownStructureCompletionRate", Integer.class);

    public final ListPath<WorkBreakdownStructure, QWorkBreakdownStructure> workBreakdownStructures = this.<WorkBreakdownStructure, QWorkBreakdownStructure>createList("workBreakdownStructures", WorkBreakdownStructure.class, QWorkBreakdownStructure.class, PathInits.DIRECT2);

    public final NumberPath<Integer> workPackageCompletionRate = createNumber("workPackageCompletionRate", Integer.class);

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bundling = inits.isInitialized("bundling") ? new QBundling(forProperty("bundling")) : null;
        this.costModel = inits.isInitialized("costModel") ? new QCostModel(forProperty("costModel")) : null;
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country"), inits.get("country")) : null;
        this.globalServiceCenter = inits.isInitialized("globalServiceCenter") ? new QGlobalServiceCenter(forProperty("globalServiceCenter")) : null;
        this.productArea = inits.isInitialized("productArea") ? new QProductArea(forProperty("productArea"), inits.get("productArea")) : null;
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
    }

}

