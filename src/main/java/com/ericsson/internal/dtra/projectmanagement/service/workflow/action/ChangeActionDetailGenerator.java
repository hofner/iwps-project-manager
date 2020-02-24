package com.ericsson.internal.dtra.projectmanagement.service.workflow.action;

@FunctionalInterface
interface ChangeActionDetailGenerator {

  /**
   * The event detail must be saved with the same format. This conformity will allow easier tracking
   * and reversion if necessary.
   * @return the event detail string
   */
  String generateEventDetail();
}
