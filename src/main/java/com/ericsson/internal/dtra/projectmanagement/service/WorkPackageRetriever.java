package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;

@Service
public class WorkPackageRetriever {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private WorkPackageRepository workPackageRepository;

  @Autowired
  private WorkBreakdownStructureRepository workBreakdownStructureRepository;

  /**
   * This method returns a specific work package based on Id of the project and work breakdown structure
   * @param projectId: Id of the project which holds the work breakdown structure
   * @param wbsId: Id of the work breakdown structure which holds the work package
   * @param wpId: Id of the wanted work package
   * @return wanted work package
   */
  public WorkPackage getWorkPackage(final Integer projectId, final Integer wbsId, final Integer wpId) {

    Optional.ofNullable(workBreakdownStructureRepository.findByProjectIdAndId(projectId, wbsId))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the work breakdown structure: " + wbsId + " of the project: " + projectId));

    return Optional.ofNullable(workPackageRepository.findByWorkBreakdownStructureIdAndId(wbsId, wpId))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the work package: " + wpId + " of the work breakdown structure: " + wbsId));
  }

  /**
   * Delete a list of Work Packages by id
   * @param projectId Id of the project these work packages belong to
   * @param wpIds List of Ids of the work packages to delete
   */
  @Transactional
  public void deleteWorkPackages(final Integer projectId, final Integer[] wpIds) {
    final Project project = Optional.ofNullable(projectRepository.findOne(projectId))
          .orElseThrow(() -> new DataRetrievalFailureException(
                "Error while fetching the project, project id doesn't exist : " + projectId));
    if (StatusEnum.PREPARATION.getStatus().equals(project.getStatus())) {
      workPackageRepository.deleteByIdIn(wpIds);
    } else {
      throw new BadRequestException("You are not allowed to delete work packages from a project when the project status is after Preparation");
    }
  }

}
