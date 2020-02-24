package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "work_package_field_change_event")
//TODO: Look into how we can benefit from Javers by saving snapshots of the entity to track each change
public class WorkPackageFieldChangeEvent extends FieldChangeEvent {

  @ManyToOne
  @JoinColumn(name = "work_package_id", nullable = false)
  @JsonBackReference
  private WorkPackage workPackage;

  WorkPackageFieldChangeEvent() {}

  public WorkPackageFieldChangeEvent(final WorkPackage workPackage) {
    this.workPackage = workPackage;
  }

  public WorkPackage getWorkPackage() {
    return workPackage;
  }

  public void setWorkPackage(WorkPackage workPackage) {
    this.workPackage = workPackage;
  }

}
