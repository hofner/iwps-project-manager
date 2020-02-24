package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.StatefulAbstractAuditableEntity;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.ProjectAction;
import com.ericsson.internal.dtra.projectmanagement.enums.ProjectEditableField;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkLevel;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkPackageEditableField;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.exception.ForbiddenWorkflowActionException;
import com.ericsson.internal.dtra.projectmanagement.service.exception.IllegalRequiredFieldAccessException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange.ProjectFieldChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange.ProjectStatusChangeAction;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.inclusion.InclusionConfigurer;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.DiffNode.State;

//TODO: to be refactored. To much duplicate with WBS and WP factory
//TODO: to interface out the common factory methods
@Service
public class ProjectChangeActionFactory {

  @Autowired
  ProjectRepository projectRepository;

  @Autowired
  ProjectStatusEventRepository projectStatusEventRepository;

  @Autowired
  ProjectFieldChangeEventRepository projectFieldChangeEventRepository;

  @Autowired
  WorkflowActionRuleRepository workflowActionRuleRepository;

  @Autowired
  WorkflowEditRuleRepository workflowEditRuleRepository;

  public List<ChangeAction> getChangeActionsToPerform(final Project currentProject, final Project updatedProject) {

    List<ChangeAction> actions = new ArrayList<>();

    // Only do a diff on the needed fields
    InclusionConfigurer comparatorFieldInclusionConfigurer = ObjectDifferBuilder.startBuilding().inclusion();
    for (ProjectEditableField field : ProjectEditableField.values()) {
      comparatorFieldInclusionConfigurer.include().propertyName(field.getFieldName());
    }
    DiffNode differences = comparatorFieldInclusionConfigurer.and().build().compare(updatedProject, currentProject);

    // create an executable for every change action needed
    for (ProjectEditableField field : ProjectEditableField.values()) {
      DiffNode difference = differences.getChild(field.getFieldName());
      if (difference != null
            && (State.CHANGED.equals(difference.getState()) || State.ADDED.equals(difference.getState()))) {
        actions.add(getAction(field, currentProject, updatedProject));
      }
    }

    return actions;
  }

  private ChangeAction getAction(
        final ProjectEditableField field, final Project currentProject,
        final Project updatedProject) {
    //TODO: verify the behavior of the inaccessible, circular states
    if (ProjectEditableField.STATUS.equals(field)) {
      // If the rule is not present, it means that the transition is inexistent
      WorkflowActionRule rule = getActionRule(currentProject.getStatus(), updatedProject.getStatus());
      return getAction(rule, currentProject, updatedProject);
    } else {
      // Get the action rule for the field update
      WorkflowEditRule rule = getEditRule(currentProject.getStatus(), field.getFieldName());

      return new ProjectFieldChangeAction(projectRepository, projectFieldChangeEventRepository, currentProject,
            updatedProject, rule, field.getFieldName(), currentProject.getStatus());
    }
  }

  private ChangeAction getAction(
        final WorkflowActionRule rule,
        final Project currentProject,
        final Project updatedProject) {
    switch (ProjectAction.fromAction(rule.getAction())) {
    case ALLOCATE:
    case SUBMIT_PLAN:
    case SUBMIT_REWORK:
      validateRequiredFieldsForAllWorkPackages(currentProject.getId(),
            Arrays.asList(WorkPackageEditableField.START_DATE.getFieldName(), WorkPackageEditableField.DUE_DATE.getFieldName()));
      break;
    case ASSIGN_SPM:
      validateRequiredFields(updatedProject, Arrays.asList(ProjectEditableField.SERVICE_PROJECT_MANAGER_SIGNUM.getFieldName(),
            ProjectEditableField.SERVICE_PROJECT_MANAGER_FULLNAME.getFieldName()));
      currentProject.setServiceProjectManagerFullname(updatedProject.getServiceProjectManagerFullname());
      currentProject.setServiceProjectManagerSignum(updatedProject.getServiceProjectManagerSignum());
      break;
    default:
      // TODO: Complete all cases
    }
    return new ProjectStatusChangeAction(currentProject, updatedProject, projectRepository,
          projectStatusEventRepository, rule);
  }

  private WorkflowActionRule getActionRule(final String currentStatus, final String newStatus) {
    return Optional
          .ofNullable(workflowActionRuleRepository.findOneByCurrentStatusAndNextStatusAndLevel(
                currentStatus, newStatus, WorkLevel.PROJECT.name()))
          .orElseThrow(() -> new BadRequestException(
                "The transition from " + currentStatus + " to " + newStatus
                      + " is not defined."));
  }

  private WorkflowEditRule getEditRule(final String currentProjectStatus, final String field) {

    WorkflowEditRule rule = workflowEditRuleRepository.findOneByProjectStatusAndFieldAndLevel(
          currentProjectStatus, field, WorkLevel.PROJECT.name());

    //TODO: find a workaround for when rule == null, but should not be editable
    // If the edit rule is null, it means we can update the field, otherwise we need to verify the project status
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

  /**
   * Validate that all the required fields are provided for all work packages, for a given project
   * @param projectId Id of the project
   * @param requiredFields Fields which should be not null
   */
  public void validateRequiredFieldsForAllWorkPackages(Integer projectId, List<String> requiredFields) {
    Project projectWithWBS = projectRepository.findOne(projectId);
    projectWithWBS.getWorkBreakdownStructures().forEach(workBreakdownStructure ->
          workBreakdownStructure.getWorkPackages().forEach(workPackage ->
                validateRequiredFields(workPackage, requiredFields)));
  }

  /**
   * Check that a list of required fields are all not-null in the provided stateful entity
   * @param updatedStatefulEntity Entity on which those fields should be validated
   * @param requiredFields Fields which are required to be not-null
   */
  // TODO: Add to generic more generic factory when possible
  public void validateRequiredFields(StatefulAbstractAuditableEntity updatedStatefulEntity, List<String> requiredFields) throws ForbiddenWorkflowActionException {
    for (String requiredField : requiredFields) {
      Field field = null;
      Object fieldValue = null;
      try {
        field = updatedStatefulEntity.getClass().getDeclaredField(requiredField);
        field.setAccessible(true);
        fieldValue = field.get(updatedStatefulEntity);
      } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
        throw new IllegalRequiredFieldAccessException("Error when trying to access field " + requiredField + " from entity " + updatedStatefulEntity.getClass().getSimpleName(), e);
      }
      if (fieldValue == null) {
        throw new ForbiddenWorkflowActionException("Field: " + field.getName() + " cannot be empty for the workflow action performed on a " + updatedStatefulEntity.getClass().getSimpleName());
      }
    }
  }

}
