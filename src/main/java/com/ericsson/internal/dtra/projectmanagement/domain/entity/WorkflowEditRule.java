package com.ericsson.internal.dtra.projectmanagement.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "workflow_edit_rule", uniqueConstraints = { @UniqueConstraint(columnNames = { "field", "level", "projectStatus" }) })
public class WorkflowEditRule {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column(nullable = false)
  private String field;

  @Column(nullable = false)
  private String level;

  @Column(nullable = false)
  private String projectStatus;

  @Column(nullable = false)
  private String permission;

  @Column(nullable = false)
  private String normalizedPermission;

  private WorkflowEditRule() {
    // Empty constructor
  }

  public WorkflowEditRule(String field, String level, String projectStatus, String permission,
        String normalizedPermission) {
    this.field = field;
    this.level = level;
    this.projectStatus = projectStatus;
    this.permission = permission;
    this.normalizedPermission = normalizedPermission;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getProjectStatus() {
    return projectStatus;
  }

  public void setProjectStatus(String projectStatus) {
    this.projectStatus = projectStatus;
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
