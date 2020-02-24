package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;

@Service
public class WorkBreakdownStructureRetriever {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private WorkBreakdownStructureRepository workBreakdownStructureRepo;

  /**
   * This method gets a specific work breakdown structure for a specific project.
   * @param projectId: project Id which holds the work breakdown structure.
   * @param wbsId: work breakdown structure Id.
   * @return wanted work breakdown structure.
   */
  public WorkBreakdownStructure getWorkBreakdownStructure(final Integer projectId, final Integer wbsId) {
    return Optional.ofNullable(workBreakdownStructureRepo.findByProjectIdAndId(projectId, wbsId))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the work breakdown structure: " + wbsId + " of the project: " + projectId));
  }

  /**
   * Delete a list of Work Breakdown Structures by id
   * @param projectId Id of the project these work breakdown structures belong to
   * @param wbsIds List of Ids of the work breakdown structures to delete
   */
  @Transactional
  public void deleteWorkBreakdownStructures(final Integer projectId, final Integer[] wbsIds) {
    final Project project = Optional.ofNullable(projectRepository.findOne(projectId))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the project, project id doesn't exist : " + projectId));
    if (StatusEnum.PREPARATION.getStatus().equals(project.getStatus())) {
      workBreakdownStructureRepo.deleteByIdIn(wbsIds);
    } else {
      throw new BadRequestException("You are not allowed to delete work breakdown structures from a project when the project status is after Preparation");
    }
  }
}
