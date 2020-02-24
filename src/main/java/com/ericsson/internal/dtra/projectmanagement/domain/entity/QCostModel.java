package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCostModel is a Querydsl query type for CostModel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCostModel extends EntityPathBase<CostModel> {

    private static final long serialVersionUID = 2052093456L;

    public static final QCostModel costModel = new QCostModel("costModel");

    public final com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity _super = new com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.QAbstractAuditableEntity(this);

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

    public final ListPath<ProductArea, QProductArea> productAreas = this.<ProductArea, QProductArea>createList("productAreas", ProductArea.class, QProductArea.class, PathInits.DIRECT2);

    public QCostModel(String variable) {
        super(CostModel.class, forVariable(variable));
    }

    public QCostModel(Path<? extends CostModel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCostModel(PathMetadata metadata) {
        super(CostModel.class, metadata);
    }

}

