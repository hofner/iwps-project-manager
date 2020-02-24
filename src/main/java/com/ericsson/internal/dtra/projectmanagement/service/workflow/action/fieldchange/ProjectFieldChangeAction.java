package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.fieldchange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectFieldChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowEditRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.FieldChangeAction;

public class ProjectFieldChangeAction extends FieldChangeAction<Project, ProjectFieldChangeEvent> {

  public ProjectFieldChangeAction(
        final ProjectRepository projectRepository,
        final ProjectFieldChangeEventRepository projectFieldChangeRepository,
        final Project currentProject,
        final Project updatedProject,
        final WorkflowEditRule editRule,
        final String field,
        final String projectStatus) {
    super(currentProject, updatedProject, projectRepository, projectFieldChangeRepository, editRule, field, projectStatus);
  }

  @Override
  @Transactional
  protected ProjectFieldChangeEvent executeFieldChange() {
    // Generate an event
    return new ProjectFieldChangeEvent(currentStatefulEntity);
  }

  @Override
  public void revert(ChangeEvent event) {
    // TODO Auto-generated method stub
  }

}
