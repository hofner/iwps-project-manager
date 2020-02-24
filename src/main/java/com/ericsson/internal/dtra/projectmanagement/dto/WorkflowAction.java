package com.ericsson.internal.dtra.projectmanagement.dto;

public class WorkflowAction {

  private String action;
  private String nextStatus;
  private String normalizedPermission;

  public WorkflowAction(String action, String nextStatus, String normalizedPermission) {
    this.action = action;
    this.nextStatus = nextStatus;
    this.normalizedPermission = normalizedPermission;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getNextStatus() {
    return nextStatus;
  }

  public void setNextStatus(String nextStatus) {
    this.nextStatus = nextStatus;
  }

  public String getNormalizedPermission() {
    return normalizedPermission;
  }

  public void setNormalizedPermission(String normalizedPermission) {
    this.normalizedPermission = normalizedPermission;
  }

}
