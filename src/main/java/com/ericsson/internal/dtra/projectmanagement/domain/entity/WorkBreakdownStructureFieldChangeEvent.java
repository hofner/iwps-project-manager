package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "work_breakdown_structure_field_change_event")
//TODO: Look into how we can benefit from Javers by saving snapshots of the entity to track each change
public class WorkBreakdownStructureFieldChangeEvent extends FieldChangeEvent {

  @ManyToOne
  @JoinColumn(name = "work_breakdown_structure_id", nullable = false)
  @JsonBackReference
  private WorkBreakdownStructure workBreakdownStructure;

  WorkBreakdownStructureFieldChangeEvent() {}

  public WorkBreakdownStructureFieldChangeEvent(final WorkBreakdownStructure workBreakdownStructure) {
    this.workBreakdownStructure = workBreakdownStructure;
  }

  public WorkBreakdownStructure getWorkBreakdownStructure() {
    return workBreakdownStructure;
  }

  public void setWorkBreakdownStructure(WorkBreakdownStructure workBreakdownStructure) {
    this.workBreakdownStructure = workBreakdownStructure;
  }

}
