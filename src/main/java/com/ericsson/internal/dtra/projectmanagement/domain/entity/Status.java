package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

  @Id
  private String name;

  private Status() {
    // Default constructor
  }

  public Status(String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

}
