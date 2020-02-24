package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.CostModelView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "cost_model")
public class CostModel extends AbstractAuditableEntity {

  @Id
  private Integer id;

  @Column(nullable = false, unique = true)
  @JsonView(CostModelView.class)
  private String name;

  @OneToMany(mappedBy = "costModel")
  @JsonManagedReference
  private List<ProductArea> productAreas = new ArrayList<>();

  public CostModel() {
    // Nothing to be done
  }

  public CostModel(final String name) {
    super();
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<ProductArea> getProductAreas() {
    return productAreas;
  }

  public void setProductAreas(final List<ProductArea> productAreas) {
    this.productAreas = productAreas;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
