package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class represents the information found for any change event type
 * instance. This will be comprised of a the details and the time
 * at which the event was triggered and by whom.
 *
 * @author egumola
 *
 */
@MappedSuperclass
public abstract class ChangeEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String details;

  @Column(nullable = false)
  private String triggeredBy;

  @Column(nullable = false)
  private Date triggeredAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getTriggeredBy() {
    return triggeredBy;
  }

  public void setTriggeredBy(String triggeredBy) {
    this.triggeredBy = triggeredBy;
  }

  public Date getTriggeredAt() {
    return triggeredAt;
  }

  public void setTriggeredAt(Date triggeredAt) {
    this.triggeredAt = triggeredAt;
  }

}
