package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkPackageEditableField;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange.WorkPackageFieldChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange.WorkPackageStatusChangeAction;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.inclusion.InclusionConfigurer;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.State;

//TODO: to be refactored. To much duplicate with WorkPackage and WBS factory
//TODO: to interface out the common factory methods
@Service
public class WorkPackageChangeActionFactory {

  @Autowired
  WorkPackageRepository workPackageRepository;

  @Autowired
  WorkPackageStatusEventRepository workPackageStatusEventRepository;

  @Autowired
  WorkPackageFieldChangeEventRepository workPackageFieldChangeEventRepository;

  @Autowired
  WorkflowActionRuleRepository workflowActionRuleRepository;

  @Autowired
  WorkflowEditRuleRepository workflowEditRuleRepository;

  public List<ChangeAction> getChangeActionsToPerform(
        final WorkPackage currentWorkPackage,
        final WorkPackage updatedWorkPackage,
        final String currentProjectStatus) {

    List<ChangeAction> actions = new ArrayList<>();

    // Only do a diff on the needed fields
    InclusionConfigurer comparatorFieldInclusionConfigurer = ObjectDifferBuilder.startBuilding().inclusion();
    for (WorkPackageEditableField field : WorkPackageEditableField.values()) {
      comparatorFieldInclusionConfigurer.include().propertyName(field.getFieldName());
    }
    DiffNode differences = comparatorFieldInclusionConfigurer
          .and()
          .build()
          .compare(updatedWorkPackage, currentWorkPackage);

    // create an executable for every change action needed
    for (WorkPackageEditableField field : WorkPackageEditableField.values()) {
      DiffNode difference = differences.getChild(field.getFieldName());
      if (difference != null
            && (State.CHANGED.equals(difference.getState()) || State.ADDED.equals(difference.getState()))) {
        actions.add(getAction(field, currentWorkPackage, updatedWorkPackage, currentProjectStatus));
      }
    }

    return actions;
  }

  private ChangeAction getAction(
        final WorkPackageEditableField field, final WorkPackage currentWorkPackage,
        final WorkPackage updatedWorkPackage, final String currentProjectStatus) {
    //TODO: verify the behavior of the inaccessible, circular states
    if (WorkPackageEditableField.STATUS.equals(field)) {
      // If the rule is not present, it means that the transition is inexistent
      WorkflowActionRule rule = getActionRule(currentWorkPackage.getStatus(), updatedWorkPackage.getStatus());
      return getAction(rule, currentWorkPackage, updatedWorkPackage);
    } else {
      // Get the action rule for the field update
      WorkflowEditRule rule = getEditRule(currentProjectStatus, field.getFieldName());

      return new WorkPackageFieldChangeAction(workPackageRepository, workPackageFieldChangeEventRepository,
            currentWorkPackage,
            updatedWorkPackage, rule, field.getFieldName(), currentWorkPackage.getStatus());
    }
  }

  private ChangeAction getAction(
        final WorkflowActionRule rule,
        final WorkPackage currentWorkPackage,
        final WorkPackage updatedWorkPackage) {
    return new WorkPackageStatusChangeAction(
          currentWorkPackage, updatedWorkPackage, workPackageRepository,
          workPackageStatusEventRepository, rule, workflowActionRuleRepository);
  }

  private WorkflowActionRule getActionRule(final String currentStatus, final String newStatus) {
    return Optional
          .ofNullable(workflowActionRuleRepository.findOneByCurrentStatusAndNextStatusAndLevel(
                currentStatus, newStatus, WorkLevel.WP.name()))
          .orElseThrow(() -> new BadRequestException(
                "The transition from " + currentStatus + " to " + newStatus
                      + " is not defined."));
  }

  private WorkflowEditRule getEditRule(final String currentProjectStatus, final String field) {

    WorkflowEditRule rule = workflowEditRuleRepository
          .findOneByProjectStatusAndFieldAndLevel(currentProjectStatus, field, WorkLevel.WP.name());

    //TODO: find a workaround for when rule == null, but should not be editable
    if (rule != null) {
      String requiredProjectStatus = rule.getProjectStatus();

      // Check the required project status
      if (!StringUtils.equals(currentProjectStatus, requiredProjectStatus)) {
        throw new BadRequestException("The field: " + field
              + " cannot be edited when the project is in the current state: " + currentProjectStatus);
      }
    }

    return rule;
  }

}
