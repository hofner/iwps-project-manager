package com.ericsson.internal.dtra.projectmanagement.service.workflow.action;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.FieldChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.StatefulAbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.exception.ForbiddenEditionActionException;

import se.ericsson.internal.csdp.authorization.utils.CSDPPermissionUtils;

/**
 * This interface represents an executable action. An executable action
 * when executed will produce an event for log purposes.
 * @author egumola
 *
 */
public abstract class FieldChangeAction<T extends StatefulAbstractAuditableEntity, S extends FieldChangeEvent>
      implements ChangeAction, Revertable
{

  private Object newValue;
  private Object currentValue;

  protected T currentStatefulEntity;
  protected T newStatefulEntity;
  protected S fieldChangeEvent;

  private JpaRepository<T, Integer> statefulEntityRepository;
  private JpaRepository<S, Integer> statefulFieldChangeEntityRepository;

  protected WorkflowEditRule editRule;
  private String field;
  private String projectStatus;

  public FieldChangeAction(
        final T currentstatefulEntity,
        final T newstatefulEntity,
        final JpaRepository<T, Integer> statefulEntityRepository,
        final JpaRepository<S, Integer> statefulFieldChangeEntityRepository,
        final WorkflowEditRule editRule,
        final String field,
        final String projectStatus) {
    this.editRule = editRule;
    this.field = field;
    this.projectStatus = projectStatus;
    this.currentStatefulEntity = currentstatefulEntity;
    this.newStatefulEntity = newstatefulEntity;
    this.statefulEntityRepository = statefulEntityRepository;
    this.statefulFieldChangeEntityRepository = statefulFieldChangeEntityRepository;
  }

  @Override
  public final synchronized void execute() {

    // If the rule is not defined, no need to check for permissions, just update the field
    if (editRule != null) {
      validateEditionRule();
    }

    // Generate the event and update the details as we go
    fieldChangeEvent = executeFieldChange();

    // Update the field reflection way, because that's how we roll
    try {
      // Get the old and new values
      Field currentField = currentStatefulEntity.getClass().getDeclaredField(field);
      Field newField = newStatefulEntity.getClass().getDeclaredField(field);

      // Remove security checks...because laws are made to be broken
      currentField.setAccessible(true);
      newField.setAccessible(true);

      currentValue = currentField.get(currentStatefulEntity);
      fieldChangeEvent.setOldValue(String.valueOf(currentValue));
      newValue = newField.get(newStatefulEntity);
      fieldChangeEvent.setNewValue(String.valueOf(newValue));

      // Update the entity with the new value
      currentField.set(currentStatefulEntity, newValue);

      // Put back security because no one else should break the law. Though, not sure if necessary
      currentField.setAccessible(false);
      newField.setAccessible(false);
    } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException exception) {
      throw new ForbiddenEditionActionException("Unable to edit the field: " + field
            + ". The field might not be available on the requested level.", exception);
    }

    fieldChangeEvent.setDetails(generateEventDetail());
    fieldChangeEvent.setTriggeredAt(new Date());
    fieldChangeEvent.setTriggeredBy(CSDPPermissionUtils.getUsername());
    fieldChangeEvent.setName(field);

    // Save the newly updated entity and its event
    statefulEntityRepository.save(currentStatefulEntity);
    statefulFieldChangeEntityRepository.save(fieldChangeEvent);
  }

  private void validateEditionRule() {

    // Make sure the current project status is good
    if (!StringUtils.equals(projectStatus, currentStatefulEntity.getStatus())) {
      throw new BadRequestException("The field: " + field
            + " cannot be edited when the project is in the current state: " + currentStatefulEntity.getStatus());
    }

    // If we don't have an edit rule for this, the field in question is not bound by more restrictive rules
    if (editRule.getPermission() != null
          // Make sure that the current user has sufficient permissions to do the action
          && !CSDPPermissionUtils.userHasFunctionPermission(editRule.getPermission())) {
      throw new ForbiddenEditionActionException(
            "You (" + CSDPPermissionUtils.getUsername() + ") do not have enough permission to edit the field: "
                  + field);
    }

  }

  /**
   * Execute the specific status change action. At this point, all
   * major validation should have been done. Moreover, actions that
   * requires special treatment must be executed at this point.
   */
  protected abstract S executeFieldChange();

  @Override
  public String generateEventDetail() {
    return CSDPPermissionUtils.getUsername() + " edited the field '" + field
          + "'. The value changed from '" + currentValue + "' to '" + newValue + "'.";
  }

}
