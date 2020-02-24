package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectStatusEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowActionRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkflowEditRuleRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory.ProjectChangeActionFactory;

import se.ericsson.internal.csdp.authorization.utils.CSDPPermissionUtils;

@Service
public class ProjectRecorder {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private WorkflowActionRuleRepository workflowActionRuleRepository;

  @Autowired
  private WorkflowEditRuleRepository workflowEditionRuleRepository;

  @Autowired
  private ProjectStatusEventRepository projectStatusEventRepository;

  @Autowired
  private ProjectFieldChangeEventRepository projectFieldChangeEventRepository;

  @Autowired
  private ProjectChangeActionFactory projectChangeActionFactory;

  /**
   * Create a new project by saving it in the database, then return it with it's new ID
   * @param project the project to be created
   * @return the created project
   */
  @Transactional
  public Project createProject(final Project project) {

    if (project.getId() != null) {
      throw new DataIntegrityViolationException("An ID was used to create the project."
            + " This forbidden value can undermine the database auto-managed ID or project integrity.");
    }
    final String cpmSignum = CSDPPermissionUtils.getUsername();
    project.setCustomerProjectManagerSignum(cpmSignum);
    project.setStatus(StatusEnum.PREPARATION.getStatus());
    //TODO by whoever takes the task for completing Audit: remove from here and get it from Auditable entity
    project.setCreatedBy(cpmSignum);
    //TODO: this set should be removed and backend should get it from frontend
    project.setCustomerProjectManagerFullname(cpmSignum);
    return projectRepository.save(project);
  }

  @Transactional
  public void update(final Integer id, final Project project) {

    if (project.getId() != null && !project.getId().equals(id)) {
      throw new BadRequestException(
            "Project id provided in the request path is " + id + " which doesn't match with id in the request body: " +
                  project.getId());
    }

    Project projectToBeUpdated = Optional.ofNullable(projectRepository.findById(id))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the project to update, project id doesn't exist : " + id));

    // Find all the actions to perform and execute them based on the fields differences
    projectChangeActionFactory.getChangeActionsToPerform(projectToBeUpdated, project).forEach(ChangeAction::execute);
  }
}
