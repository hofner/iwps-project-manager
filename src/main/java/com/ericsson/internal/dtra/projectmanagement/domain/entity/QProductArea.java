package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductArea is a Querydsl query type for ProductArea
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductArea extends EntityPathBase<ProductArea> {

    private static final long serialVersionUID = -172988464L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductArea productArea = new QProductArea("productArea");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

    public final QCostModel costModel;

    //inherited
    public final DateTimePath<java.util.Date> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public QProductArea(String variable) {
        this(ProductArea.class, forVariable(variable), INITS);
    }

    public QProductArea(Path<? extends ProductArea> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductArea(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductArea(PathMetadata metadata, PathInits inits) {
        this(ProductArea.class, metadata, inits);
    }

    public QProductArea(Class<? extends ProductArea> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.costModel = inits.isInitialized("costModel") ? new QCostModel(forProperty("costModel")) : null;
    }

}

