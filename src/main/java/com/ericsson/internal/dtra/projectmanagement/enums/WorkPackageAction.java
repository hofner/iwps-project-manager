package com.ericsson.internal.dtra.projectmanagement.enums;

public enum WorkPackageAction {

  FULFILL_WP("Fulfill WP"),
  ACCEPT_DELIVERY("Accept Delivery"),
  DELIVER_WP("Deliver WP"),
  CANCEL_WP("Cancel WP"),
  ORDER_WP("Order WP"),
  REWORK_WP("Rework WP"),
  REJECT_DELIVERY("Reject Delivery");

  private String action;

  private WorkPackageAction(final String action) {
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
  public static WorkPackageAction fromAction(final String action) {
    for (WorkPackageAction status : WorkPackageAction.values()) {
      if (status.getAction().equals(action)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The action " + action + " is undefined.");
  }
}
