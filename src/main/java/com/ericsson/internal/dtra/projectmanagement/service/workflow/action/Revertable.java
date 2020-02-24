package com.ericsson.internal.dtra.projectmanagement.service.workflow.action;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;

/**
 * This interface represents an action that can be undone. The action
 * will take a change event as a parameter and undo it.
 * @author egumola
 *
 */
@FunctionalInterface
interface Revertable {

  /**
   * Revert an event. If the event is the latest in history, it will be discarded and the concerned
   * value must be properly set back. If the event happen at a certain point in history that is
   * not the latest event, then we throw and {@link IllegalArgumentException}
   * @param event an instance of {@link ChangeEvent} with the details of how to revert the change
   */
  void revert(final ChangeEvent event);
}
