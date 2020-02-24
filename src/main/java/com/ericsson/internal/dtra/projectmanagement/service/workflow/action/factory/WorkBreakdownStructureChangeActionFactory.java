package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkBreakdownStructureEditableField;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange.WorkBreakdownStructureFieldChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange.WorkBreakdownStructureStatusChangeAction;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.inclusion.InclusionConfigurer;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.State;

//TODO: to be refactored. To much duplicate with WBS and WP factory
//TODO: to interface out the common factory methods
@Service
public class WorkBreakdownStructureChangeActionFactory {

  @Autowired
  WorkBreakdownStructureRepository workBreakdownStructureRepository;

  @Autowired
  WorkBreakdownStructureStatusEventRepository workBreakdownStructureStatusEventRepository;

  @Autowired
  WorkBreakdownStructureFieldChangeEventRepository workBreakdownStructureFieldChangeEventRepository;

  @Autowired
  WorkflowActionRuleRepository workflowActionRuleRepository;

  @Autowired
  WorkflowEditRuleRepository workflowEditRuleRepository;

  public List<ChangeAction> getChangeActionsToPerform(
        final WorkBreakdownStructure currentWorkBreakdownStructure,
        final WorkBreakdownStructure updatedWorkBreakdownStructure,
        final String currentProjectStatus) {

    List<ChangeAction> actions = new ArrayList<>();

    // Only do a diff on the needed fields
    InclusionConfigurer comparatorFieldInclusionConfigurer = ObjectDifferBuilder.startBuilding().inclusion();
    for (WorkBreakdownStructureEditableField field : WorkBreakdownStructureEditableField.values()) {
      comparatorFieldInclusionConfigurer.include().propertyName(field.getFieldName());
    }
    DiffNode differences = comparatorFieldInclusionConfigurer
          .and()
          .build()
          .compare(updatedWorkBreakdownStructure, currentWorkBreakdownStructure);

    // create an executable for every change action needed
    for (WorkBreakdownStructureEditableField field : WorkBreakdownStructureEditableField.values()) {
      DiffNode difference = differences.getChild(field.getFieldName());
      if (difference != null
            && (State.CHANGED.equals(difference.getState()) || State.ADDED.equals(difference.getState()))) {
        actions.add(getAction(
              field, currentWorkBreakdownStructure,
              updatedWorkBreakdownStructure, currentProjectStatus));
      }
    }

    return actions;
  }

  private ChangeAction getAction(
        final WorkBreakdownStructureEditableField field, final WorkBreakdownStructure currentWorkBreakdownStructure,
        final WorkBreakdownStructure updatedWorkBreakdownStructure, final String currentProjectStatus) {
    //TODO: verify the behavior of the inaccessible, circular states
    if (WorkBreakdownStructureEditableField.STATUS.equals(field)) {
      // If the rule is not present, it means that the transition is inexistent
      WorkflowActionRule rule = getActionRule(currentWorkBreakdownStructure.getStatus(),
            updatedWorkBreakdownStructure.getStatus());
      return getAction(rule, currentWorkBreakdownStructure, updatedWorkBreakdownStructure);
    } else {
      // Get the action rule for the field update
      WorkflowEditRule rule = getEditRule(currentProjectStatus, field.getFieldName());

      return new WorkBreakdownStructureFieldChangeAction(workBreakdownStructureRepository,
            workBreakdownStructureFieldChangeEventRepository, currentWorkBreakdownStructure,
            updatedWorkBreakdownStructure, rule, field.getFieldName(), currentWorkBreakdownStructure.getStatus());
    }
  }

  private ChangeAction getAction(
        final WorkflowActionRule rule,
        final WorkBreakdownStructure currentWorkBreakdownStructure,
        final WorkBreakdownStructure updatedWorkBreakdownStructure) {
    // Any workBreakdownStructure action has a similar execution, use a switch to handle other cases
    return new WorkBreakdownStructureStatusChangeAction(currentWorkBreakdownStructure, updatedWorkBreakdownStructure,
          workBreakdownStructureRepository,
          workBreakdownStructureStatusEventRepository, rule);
  }

  private WorkflowActionRule getActionRule(final String currentStatus, final String newStatus) {
    return Optional
          .ofNullable(workflowActionRuleRepository.findOneByCurrentStatusAndNextStatusAndLevel(
                currentStatus, newStatus, WorkLevel.WBS.name()))
          .orElseThrow(() -> new BadRequestException(
                "The transition from " + currentStatus + " to " + newStatus
                      + " is not defined."));
  }

  private WorkflowEditRule getEditRule(final String currentProjectStatus, final String field) {

    WorkflowEditRule rule = workflowEditRuleRepository.findOneByProjectStatusAndFieldAndLevel(
          currentProjectStatus, field, WorkLevel.WBS.name());

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
