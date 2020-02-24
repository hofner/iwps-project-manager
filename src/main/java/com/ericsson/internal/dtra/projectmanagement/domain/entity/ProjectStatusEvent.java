package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "project_status_event")
//TODO: Look into how we can benefit from Javers by saving snapshots of the entity to track each change
//TODO: Change the name of the entity and the database table to reflect a change event
public class ProjectStatusEvent extends StatusChangeEvent {

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  @JsonBackReference
  private Project project;

  ProjectStatusEvent() {}

  public ProjectStatusEvent(final Project project) {
    this.project = project;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
