package com.ericsson.internal.dtra.projectmanagement.service.workflow.action.statuschange;

import javax.transaction.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectStatusEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkflowActionRule;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.StatusChangeAction;

public class ProjectStatusChangeAction extends StatusChangeAction<Project, ProjectStatusEvent> {

  public ProjectStatusChangeAction(
        final Project currentProject,
        final Project updatedProject,
        final ProjectRepository projectRepository,
        final ProjectStatusEventRepository projectStatusEventRepository,
        final WorkflowActionRule workflowActionRule) {
    super(workflowActionRule, projectRepository, projectStatusEventRepository, currentProject, updatedProject);
  }

  @Override
  @Transactional
  protected ProjectStatusEvent executeStatusChange() {
    return new ProjectStatusEvent(currentStatefulEntity);
  }

}
