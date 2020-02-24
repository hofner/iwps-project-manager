package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "workflow_action_rule", uniqueConstraints = { @UniqueConstraint(columnNames = { "currentStatus", "nextStatus", "level" }) })
public class WorkflowActionRule {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column(nullable = false)
  private String currentStatus;

  @Column(nullable = false)
  private String nextStatus;

  @Column(nullable = false)
  private String action;

  @Column(nullable = false)
  private String level;

  @Column(nullable = false)
  private String permission;

  @Column(nullable = false)
  private String normalizedPermission;

  private WorkflowActionRule() {
    // Default constructor
  }

  public WorkflowActionRule(String currentStatus, String nextStatus, String action,
        String level, String permission, String normalizedPermission) {
    this.currentStatus = currentStatus;
    this.nextStatus = nextStatus;
    this.action = action;
    this.level = level;
    this.permission = permission;
    this.normalizedPermission = normalizedPermission;
  }

  public final Integer getId() {
    return id;
  }

  public final void setId(final Integer id) {
    this.id = id;
  }

  public final String getCurrentStatus() {
    return currentStatus;
  }

  public final void setCurrentStatus(final String currentStatus) {
    this.currentStatus = currentStatus;
  }

  public final String getNextStatus() {
    return nextStatus;
  }

  public final void setNextStatus(final String nextStatus) {
    this.nextStatus = nextStatus;
  }

  public final String getAction() {
    return action;
  }

  public final void setAction(final String action) {
    this.action = action;
  }

  public final String getLevel() {
    return level;
  }

  public final void setLevel(final String level) {
    this.level = level;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getNormalizedPermission() {
    return normalizedPermission;
  }

  public void setNormalizedPermission(String normalizedPermission) {
    this.normalizedPermission = normalizedPermission;
  }

}
