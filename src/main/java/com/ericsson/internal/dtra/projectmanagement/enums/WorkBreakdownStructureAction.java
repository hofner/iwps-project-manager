package com.ericsson.internal.dtra.projectmanagement.enums;

public enum WorkBreakdownStructureAction {

  CANCEL_WP("Cancel WP"),
  CLOSE_WBS("Close WBS");

  private String action;

  private WorkBreakdownStructureAction(final String action) {
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
  public static WorkBreakdownStructureAction fromAction(final String action) {
    for (WorkBreakdownStructureAction status : WorkBreakdownStructureAction.values()) {
      if (status.getAction().equals(action)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The action " + action + " is undefined.");
  }
}
