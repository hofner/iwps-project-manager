package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackageStatusEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.enums.WorkPackageAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.StatusChangeAction;

public class WorkPackageStatusChangeAction extends StatusChangeAction<WorkPackage, WorkPackageStatusEvent> {

  private WorkflowActionRuleRepository workflowActionRuleRepository;
  private WorkflowActionRule workflowActionRule;
  private WorkPackageRepository workPackageRepository;

  public WorkPackageStatusChangeAction(
        final WorkPackage currentWorkPackage,
        final WorkPackage updatedWorkPackage,
        final WorkPackageRepository workPackageRepository,
        final WorkPackageStatusEventRepository workPackageStatusEventRepository,
        final WorkflowActionRule workflowActionRule,
        final WorkflowActionRuleRepository workflowActionRuleRepository) {
    super(workflowActionRule, workPackageRepository, workPackageStatusEventRepository, currentWorkPackage,
          updatedWorkPackage);
    this.workPackageRepository = workPackageRepository;
    this.workflowActionRuleRepository = workflowActionRuleRepository;
    this.workflowActionRule = workflowActionRule;
  }

  @Override
  @Transactional
  protected WorkPackageStatusEvent executeStatusChange() {
    final String expectedFutureStatus = newStatefulEntity.getStatus();
    WorkBreakdownStructure wbs = currentStatefulEntity.getWorkBreakdownStructure();

    if (workflowActionRuleRepository
          .existsByCurrentStatusAndNextStatusAndAction(wbs.getStatus(), expectedFutureStatus, "AUTOMATIC")) {
      handleAutomaticActions(wbs, expectedFutureStatus);
    }

    return new WorkPackageStatusEvent(currentStatefulEntity);
  }

  /**
   * An automatic action needs to happen on the parent entity when all the children or one of them changes
   * status based on an action
   * @param wbs the breakdown structure (parent)
   * @param expectedFutureStatus the expected next status
   */
  private void handleAutomaticActions(final WorkBreakdownStructure wbs, final String expectedFutureStatus) {

    // Verify if an automatic action rule is set to update the wbs
    switch (WorkPackageAction.fromAction(workflowActionRule.getAction())) {
    case ACCEPT_DELIVERY:
    case DELIVER_WP:
    case ORDER_WP:
      if (shouldUpdateWbsForUpdatedChildren(wbs, expectedFutureStatus)) {
        wbs.setStatus(expectedFutureStatus);
      }
      // Make sure the project goes to fulfill wp order if there is at least one WP in ordering
      wbs.getProject().setStatus(StatusEnum.FULFILL_WP_ORDER.getStatus());
      break;
    case REJECT_DELIVERY:
      wbs.setStatus(expectedFutureStatus);
      break;
    default:
      break;
    }

  }

  private boolean shouldUpdateWbsForUpdatedChildren(final WorkBreakdownStructure wbs, final String expectedFutureStatus) {
    int wpCount = workPackageRepository
          .countByWorkBreakdownStructureIdAndStatus(wbs.getId(), expectedFutureStatus);
    //TODO: count for the WBS instead of fetching every single WP
    return wpCount + 1 == wbs.getWorkPackages().size();
  }

}
