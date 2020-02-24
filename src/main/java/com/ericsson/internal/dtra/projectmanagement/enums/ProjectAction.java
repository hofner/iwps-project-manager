package com.ericsson.internal.dtra.projectmanagement.enums;

public enum ProjectAction {

  SUBMIT_CR("Submit CR"),
  CREATE_CR("Create CR"),
  ASSIGN_SPM("Assign SPM"),
  REJECT_PLAN_REWORK("Reject Plan Rework"),
  CLOSE_PROJECT("Close Project"),
  CANCEL_PROJECT("Cancel Project"),
  ACCEPT_PLAN("Accept Plan"),
  REJECT_PROJECT("Reject Project"),
  REJECT_CR("Reject CR"),
  ALLOCATE("Allocate"),
  SUBMIT_PLAN("Submit Plan"),
  REWORK_PLAN("Rework Plan"),
  SUBMIT_REWORK("Submit Rework");

  private String action;

  private ProjectAction(final String action) {
    this.action = action;
  }

  public String getAction() {
    return this.action;
  }

  /**
   * Return the enum based on it's action value instead of the name.
   * @param action the action
   * @return the enum corresponding to the action
   */
  public static ProjectAction fromAction(final String action) {
    for (ProjectAction status : ProjectAction.values()) {
      if (status.getAction().equals(action)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The action " + action + " is undefined.");
  }
}
