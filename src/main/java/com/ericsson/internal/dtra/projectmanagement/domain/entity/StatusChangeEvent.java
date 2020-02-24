package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This class represents the commonly shared fields by any status edition event
 * instance. This will be comprised of a the old status and the new
 * one. A detail message is also kept for more concise information.
 *
 * @author egumola
 *
 */
@MappedSuperclass
public abstract class StatusChangeEvent extends ChangeEvent {

  @Column(nullable = false)
  private String oldStatus;

  @Column(nullable = false)
  private String newStatus;

  private String reason;

  public String getOldStatus() {
    return oldStatus;
  }

  public void setOldStatus(String oldStatus) {
    this.oldStatus = oldStatus;
  }

  public String getNewStatus() {
    return newStatus;
  }

  public void setNewStatus(String newStatus) {
    this.newStatus = newStatus;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
