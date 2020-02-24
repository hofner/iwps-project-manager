package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This class represents the commonly shared fields by any field edition event
 * instance. This will be comprised of a field name, the old value and the new
 * one.
 *
 * @author egumola
 *
 */
@MappedSuperclass
public abstract class FieldChangeEvent extends ChangeEvent {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String oldValue;

  @Column(nullable = false)
  private String newValue;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOldValue() {
    return oldValue;
  }

  public void setOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

}
