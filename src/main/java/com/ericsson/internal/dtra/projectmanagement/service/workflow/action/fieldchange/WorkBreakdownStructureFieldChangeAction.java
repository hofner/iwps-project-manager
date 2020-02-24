package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructureFieldChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.FieldChangeAction;

public class WorkBreakdownStructureFieldChangeAction
      extends FieldChangeAction<WorkBreakdownStructure, WorkBreakdownStructureFieldChangeEvent> {

  public WorkBreakdownStructureFieldChangeAction(
        final WorkBreakdownStructureRepository workBreakdownStructureRepository,
        final WorkBreakdownStructureFieldChangeEventRepository workBreakdownStructureFieldChangeRepository,
        final WorkBreakdownStructure currentWorkBreakdownStructure,
        final WorkBreakdownStructure updatedWorkBreakdownStructure,
        final WorkflowEditRule editRule,
        final String field,
        final String projectStatus) {
    super(currentWorkBreakdownStructure, updatedWorkBreakdownStructure,
          workBreakdownStructureRepository, workBreakdownStructureFieldChangeRepository,
          editRule, field, projectStatus);
  }

  @Override
  @Transactional
  protected WorkBreakdownStructureFieldChangeEvent executeFieldChange() {
    // Generate an event
    return new WorkBreakdownStructureFieldChangeEvent(currentStatefulEntity);
  }

  @Override
  public void revert(ChangeEvent event) {
    // TODO Auto-generated method stub
  }

}
