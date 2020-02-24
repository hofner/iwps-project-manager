package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProductAreaView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "product_area")
public class ProductArea extends AbstractAuditableEntity {

  @Id
  @JsonView(ProductAreaView.class)
  private Integer id;

  @Column(nullable = false, unique = true)
  @JsonView({ ProjectView.class, ProductAreaView.class })
  private String name;

  @ManyToOne
  @JoinColumn(name = "cost_model_id", nullable = false)
  @JsonBackReference
  @JsonView({ ProjectView.class, ProductAreaView.class })
  private CostModel costModel;

  public ProductArea() {
    // Nothing to be done
  }

  public ProductArea(final String name, final CostModel costModel) {
    super();
    this.name = name;
    this.costModel = costModel;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CostModel getCostModel() {
    return costModel;
  }

  public void setCostModel(CostModel costModel) {
    this.costModel = costModel;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
