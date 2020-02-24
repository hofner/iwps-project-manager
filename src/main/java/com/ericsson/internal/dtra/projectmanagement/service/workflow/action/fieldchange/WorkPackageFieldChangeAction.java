package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackageFieldChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.FieldChangeAction;

public class WorkPackageFieldChangeAction extends FieldChangeAction<WorkPackage, WorkPackageFieldChangeEvent> {

  public WorkPackageFieldChangeAction(
        final WorkPackageRepository workPackageRepository,
        final WorkPackageFieldChangeEventRepository workPackageFieldChangeRepository,
        final WorkPackage currentWorkPackage,
        final WorkPackage updatedWorkPackage,
        final WorkflowEditRule editRule,
        final String field,
        final String projectStatus) {
    super(currentWorkPackage, updatedWorkPackage, workPackageRepository,
          workPackageFieldChangeRepository, editRule, field, projectStatus);
  }

  @Override
  @Transactional
  protected WorkPackageFieldChangeEvent executeFieldChange() {
    // Generate an event
    return new WorkPackageFieldChangeEvent(currentStatefulEntity);
  }

  @Override
  public void revert(ChangeEvent event) {
    // TODO Auto-generated method stub
  }

}
