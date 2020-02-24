package com.ericsson.internal.dtra.projectmanagement.service.workflow.action;

/**
 * This interface represents an executable action. An executable action
 * when executed will produce an event for log purposes.
 * @author egumola
 *
 */
@FunctionalInterface
public interface Executable {

  /**
   * Execute the action. On successful execution, the action will generate
   * a change event. This event will contain the detailed summary of the executed
   * action, when it was triggered and by whom.
   */
  void execute();
}
