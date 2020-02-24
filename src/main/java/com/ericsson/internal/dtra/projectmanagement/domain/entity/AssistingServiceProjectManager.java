package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "assisting_service_project_manager", uniqueConstraints = {
      @UniqueConstraint(columnNames = { "signum", "fullname" }) })
public class AssistingServiceProjectManager extends AbstractAuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonView(ProjectDetailView.class)
  @Column(nullable = false)
  private String signum;

  @JsonView(ProjectDetailView.class)
  @Column(name = "fullname", nullable = false)
  private String fullName;

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getSignum() {
    return signum;
  }

  public void setSignum(final String signum) {
    this.signum = signum;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(final String fullName) {
    this.fullName = fullName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
    result = prime * result + ((signum == null) ? 0 : signum.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AssistingServiceProjectManager other = (AssistingServiceProjectManager) obj;
    if (fullName == null) {
      if (other.fullName != null)
        return false;
    } else if (!fullName.equals(other.fullName))
      return false;
    if (signum == null) {
      if (other.signum != null)
        return false;
    } else if (!signum.equals(other.signum))
      return false;
    return true;
  }
}