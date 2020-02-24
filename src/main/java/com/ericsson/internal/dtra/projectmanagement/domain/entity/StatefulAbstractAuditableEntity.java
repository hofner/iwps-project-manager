package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.audit.AbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkBreakdownStructureView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkPackageView;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Interface defining the methods required by all state entities. Mostly
 * the get/set status.
 *
 * @author egumola
 *
 */
@MappedSuperclass
public abstract class StatefulAbstractAuditableEntity extends AbstractAuditableEntity {

  @Column(nullable = false)
  @JsonView({ ProjectView.class, WorkPackageView.class, WorkBreakdownStructureView.class })
  protected String status;

  @Transient
  private String statusChangeReason;

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public String getStatusChangeReason() {
    return statusChangeReason;
  }

  public void setStatusChangeReason(String statusChangeReason) {
    this.statusChangeReason = statusChangeReason;
  }

}
