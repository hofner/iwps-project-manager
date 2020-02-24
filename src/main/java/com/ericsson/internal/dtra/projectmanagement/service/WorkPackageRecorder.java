package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory.WorkPackageChangeActionFactory;

@Service
public class WorkPackageRecorder {

  private static final String INITIAL_STATUS = StatusEnum.INITIALIZED.getStatus();

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private WorkPackageRepository workPackageRepository;

  @Autowired
  private WorkPackageRetriever workPackageRetriever;

  @Autowired
  private WorkPackageChangeActionFactory workPackageChangeActionFactory;

  /**
   * This method gets a list of compressed work packages and saves them
   * @param projectId: Id of the project
   * @param wbsId: Id of the work breakdown structure
   * @param compressedWorkPackages: List of work package which are bunch of samples and a requested number for each sample.
   *        For example 5 * WP1
   * @return List of saved work packages
   */
  public List<WorkPackage> createWorkPackages(final Integer projectId, final Integer wbsId,
        final List<WorkPackage> compressedWorkPackages) {

    try {
      final Project project = Optional.ofNullable(projectRepository.findOne(projectId))
            .orElseThrow(() -> new DataRetrievalFailureException(
                  "Error while fetching the project, project id doesn't exist : " + projectId));

      validateProjectStatus(project.getStatus());

      final WorkBreakdownStructure workBreakdownStructure = project.getWorkBreakdownStructures().stream()
            .filter(wbs -> wbs.getId().equals(wbsId)).findAny()
            .orElseThrow(() -> new DataRetrievalFailureException(
                  "Wanted work breakdown structure " + wbsId + " doesn't belong to project id " + projectId));

      validateWorkBreakdownStructureStatus(workBreakdownStructure.getStatus());
      List<WorkPackage> uncompressedWorkPackages = uncompressWorkPackages(compressedWorkPackages, workBreakdownStructure);
      return workPackageRepository.save(uncompressedWorkPackages);
    } catch (DataAccessResourceFailureException ex) {
      throw new DataAccessResourceFailureException("We encounterd a problem while accessing the database", ex);
    }
  }

  /**
   * This method extracts the compressed work packages
   * @param compressedWorkPackages: sample of work packages with the requested number of them like 5*IWP1
   * @param workBreakdownStructure: the work break down structure which holds the work packages
   * @return list of work packages
   */
  protected List<WorkPackage> uncompressWorkPackages(final List<WorkPackage> compressedWorkPackages,
        final WorkBreakdownStructure workBreakdownStructure) {
    List<WorkPackage> uncompressedWorkPackages = new LinkedList<>();
    compressedWorkPackages.forEach(
          cwp -> IntStream.rangeClosed(1, cwp.getRequestedCount())
                .forEach(i -> uncompressedWorkPackages.add(new WorkPackage.WorkPackageBuilder(workBreakdownStructure,
                      cwp.getName(), cwp.getVersion(), INITIAL_STATUS, cwp.getCode()).build())));
    return uncompressedWorkPackages;
  }

  /**
   * This method verifies if the work breakdown structure status is valid for adding the work packages
   * @param workBreakdownStructureStatus: work breakdown structure status
   */
  private void validateWorkBreakdownStructureStatus(final String workBreakdownStructureStatus) {
    if (!(INITIAL_STATUS.equals(workBreakdownStructureStatus))) {
      throw new BadRequestException(
            "You are not allowed to add work packages to a work breakdown structure with status " +
                  workBreakdownStructureStatus);
    }
  }

  /**
   * This method verifies that if the user is allowed to add work packages for the current project status
   * @param projectStatus the current status of the project
   */
  private void validateProjectStatus(final String projectStatus) {
    if (!StatusEnum.PLAN_SUBMITTED.getStatus().equals(projectStatus) && !StatusEnum.PREPARATION.getStatus().equals(projectStatus)) {
      throw new BadRequestException(
            "You are not allowed to add work packages to a project with status " + projectStatus);
    }
  }

  /**
   * This method updates a work package
   * @param projectId: Id of the project which holds the work package
   * @param wbsId: Id of the work breakdown structure which holds the work package
   * @param wpId: Id of the wanted work package to get updated
   * @param workPackage: update information for wanted work package
   */
  public void updateWorkPackage(final Integer projectId, final Integer wbsId, final Integer wpId,
        final WorkPackage workPackage) {

    //TODO: error handling
    if (workPackage.getId() != null && !workPackage.getId().equals(wpId)) {
      throw new BadRequestException(
            "Work package id provided in the request path is " + wpId
                  + " which doesn't match with id in the request body: " +
                  workPackage.getId());
    }
    WorkPackage workPackageToBeUpdated = workPackageRetriever.getWorkPackage(projectId, wbsId, wpId);

    workPackageChangeActionFactory
          .getChangeActionsToPerform(workPackageToBeUpdated, workPackage, projectRepository.findStatusById(projectId))
          .forEach(ChangeAction::execute);
  }
}
