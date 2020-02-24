package com.ericsson.internal.dtra.projectmanagement.service.workflow.action;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.StatefulAbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.StatusChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.service.exception.ForbiddenWorkflowActionException;

import se.ericsson.internal.csdp.authorization.utils.CSDPPermissionUtils;

/**
 * This interface represents an executable action. An executable action
 * when executed will produce an event for log purposes.
 *
 * @author egumola
 *
 */
public abstract class StatusChangeAction<T extends StatefulAbstractAuditableEntity, S extends StatusChangeEvent>
      implements ChangeAction {

  private WorkflowActionRule workflowActionRule;

  protected T currentStatefulEntity;
  protected T newStatefulEntity;
  protected S statusChangeEvent;

  private JpaRepository<T, Integer> statefulEntityRepository;
  private JpaRepository<S, Integer> statefulStatusChangeEntityRepository;

  public StatusChangeAction(
        final WorkflowActionRule workflowActionRule,
        final JpaRepository<T, Integer> statefulEntityRepository,
        final JpaRepository<S, Integer> statefulStatusChangeEntityRepository,
        final T currentStatefulEntity,
        final T newStatefulEntity) {
    this.workflowActionRule = workflowActionRule;
    this.newStatefulEntity = newStatefulEntity;
    this.currentStatefulEntity = currentStatefulEntity;
    this.statefulStatusChangeEntityRepository = statefulStatusChangeEntityRepository;
    this.statefulEntityRepository = statefulEntityRepository;
  }

  @Override
  public final synchronized void execute() {
    // Make sure that the current user has sufficient permissions to do the action
    if (!CSDPPermissionUtils.userHasFunctionPermission(workflowActionRule.getPermission())) {
      throw new ForbiddenWorkflowActionException(
            "You (" + CSDPPermissionUtils.getUsername() + ") are not allowed to perform the workflow action: "
                  + workflowActionRule.getAction());
    }

    // Generate the status change event
    statusChangeEvent = executeStatusChange();
    statusChangeEvent.setOldStatus(currentStatefulEntity.getStatus());
    statusChangeEvent.setTriggeredBy(CSDPPermissionUtils.getUsername());
    statusChangeEvent.setReason(currentStatefulEntity.getStatusChangeReason());
    statusChangeEvent.setDetails(generateEventDetail());

    // Update the entity
    currentStatefulEntity.setStatus(newStatefulEntity.getStatus());
    statefulEntityRepository.save(currentStatefulEntity);

    statusChangeEvent.setNewStatus(newStatefulEntity.getStatus());
    statusChangeEvent.setTriggeredAt(new Date());
    statefulStatusChangeEntityRepository.save(statusChangeEvent);
  }

  /**
   * Execute the specific status change action. At this point, all
   * major validation should have been done. Moreover, actions that
   * requires special treatment must be executed at this point.
   */
  protected abstract S executeStatusChange();

  @Override
  public String generateEventDetail() {
    return CSDPPermissionUtils.getUsername() + " did the action '" + workflowActionRule.getAction()
          + "'. The status changed from '" + workflowActionRule.getCurrentStatus() + "' to '"
          + workflowActionRule.getNextStatus() + "'."
          + (statusChangeEvent.getReason() != null ? " The reason provided was: " + currentStatefulEntity.getStatusChangeReason() : "");
  }

}
