package com.ericsson.internal.dtra.projectmanagement.enums;

public enum StatusEnum {

  PREPARATION("Preparation"),
  DEMAND_SUBMITTED("Demand Submitted"),
  PLANNED("Planned"),
  PLAN_SUBMITTED("Plan Submitted"),
  PLAN_REWORK_PREPARATION("Plan Rework Preparation"),
  PLAN_REWORKED("Plan Reworked"),
  PLAN_ACCEPTED("Plan Accepted"),
  FULFILL_WP_ORDER("Fulfill WP Order"),
  DELIVERY_ACCEPTED("Delivery Accepted"),
  INITIALIZED("Initialized"),
  ORDERED("Ordered"),
  COMPLETED("Completed"),
  DELIVERED("Delivered"),
  FULFILL_WP("Fulfill WP"),
  CR_PREPARATION("CR Preparation"),
  CR_SUBMITTED("CR Submitted"),
  SCHEDULED("Scheduled"),
  DISPATCHED("Dispatched"),
  IN_PROGRESS("In Progress"),
  CANCELLED("Cancelled"),
  CLOSED("Closed");

  private String status;

  StatusEnum(final String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }

  /**
   * Return the enum based on it's value instead of the name.
   * @param value the value i.e. softwareRelease
   * @return the enum corresponding to the value
   */
  public static StatusEnum fromValue(final String value) {
    for (StatusEnum status : StatusEnum.values()) {
      if (status.getStatus().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The status " + value + " is undefined.");
  }

}
