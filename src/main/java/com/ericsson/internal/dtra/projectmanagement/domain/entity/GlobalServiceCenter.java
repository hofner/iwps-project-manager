package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "global_service_center")
public class GlobalServiceCenter extends AbstractAuditableEntity {

  @Id
  private Integer id;

  @Column(nullable = false, unique = true)
  @JsonView(ProjectDetailView.class)
  private String name;

  @Column(nullable = false, unique = true)
  @JsonView(ProjectView.class)
  private String displayName;

  public GlobalServiceCenter() {
    // Nothing to be done
  }

  public GlobalServiceCenter(final String name) {
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

  @Override
  public String toString() {
    return this.name;
  }

}
