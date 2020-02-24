package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructureStatusEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.StatusChangeAction;

public class WorkBreakdownStructureStatusChangeAction
      extends StatusChangeAction<WorkBreakdownStructure, WorkBreakdownStructureStatusEvent> {

  public WorkBreakdownStructureStatusChangeAction(
        final WorkBreakdownStructure currentWorkBreakdownStructure,
        final WorkBreakdownStructure updatedWorkBreakdownStructure,
        final WorkBreakdownStructureRepository workBreakdownStructureRepository,
        final WorkBreakdownStructureStatusEventRepository workBreakdownStructureStatusEventRepository,
        final WorkflowActionRule workflowActionRule) {
    super(workflowActionRule, workBreakdownStructureRepository, workBreakdownStructureStatusEventRepository,
          currentWorkBreakdownStructure, updatedWorkBreakdownStructure);
  }

  @Override
  @Transactional
  protected WorkBreakdownStructureStatusEvent executeStatusChange() {
    return new WorkBreakdownStructureStatusEvent(currentStatefulEntity);
  }

}
