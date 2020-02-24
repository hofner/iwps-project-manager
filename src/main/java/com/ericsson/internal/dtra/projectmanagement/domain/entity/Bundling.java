package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "bundling", uniqueConstraints = @UniqueConstraint(columnNames = { "code", "description" }))
public class Bundling extends AbstractAuditableEntity {

  @Id
  private Integer id;

  @Column(nullable = false)
  private Integer code;

  @Column(nullable = false)
  @JsonView(ProjectDetailView.class)
  private String description;

  public Bundling() {
    // Nothing to be done
  }

  public Bundling(final Integer code, final String description) {
    super();
    this.code = code;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(final Integer code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.description;
  }

}
