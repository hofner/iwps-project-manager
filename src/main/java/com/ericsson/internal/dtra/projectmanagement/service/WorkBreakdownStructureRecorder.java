package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageRepository;
import com.ericsson.internal.dtra.projectmanagement.enums.StatusEnum;
import com.ericsson.internal.dtra.projectmanagement.service.exception.BadRequestException;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.ChangeAction;
import com.ericsson.internal.dtra.projectmanagement.service.workflow.action.factory.WorkBreakdownStructureChangeActionFactory;

@Service
public class WorkBreakdownStructureRecorder {

  private static final String INITIAL_STATUS = StatusEnum.INITIALIZED.getStatus();
  private static final Integer WBS_CLONE_LIMIT = 10;

  @Autowired
  private WorkBreakdownStructureRepository workBreakdownStructureRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private WorkBreakdownStructureRetriever workBreakdownStructureRetriever;

  @Autowired
  private WorkBreakdownStructureChangeActionFactory wbsChangeActionFactory;

  @Autowired
  private WorkPackageRepository workPackageRepository;

  /**
   * This method gets a work breakdown structure with its own work packages and saves them in database
   * Each work package is a bunch of samples and a requested number for each sample, for example 5 * WP1
   * @param workBreakdownStructure: WBS with sample of WPs
   * @param projectId: id of the project
   * @return a work break down structure that has been saved.
   */
  public WorkBreakdownStructure createWorkBreakdownStructure(final Integer projectId,
        WorkBreakdownStructure workBreakdownStructure) {

    try {
      final Project project = Optional.ofNullable(projectRepository.findOne(projectId))
            .orElseThrow(() -> new DataRetrievalFailureException(
                  "Error while fetching the project, project id doesn't exist : " + projectId));

      validateProjectStatus(project.getStatus());
      uncompressWorkPackages(workBreakdownStructure);
      workBreakdownStructure.setStatus(INITIAL_STATUS);
      workBreakdownStructure.setProject(project);

      return workBreakdownStructureRepository.save(workBreakdownStructure);
    } catch (DataAccessResourceFailureException ex) {
      throw new DataAccessResourceFailureException("We encounterd a problem while accessing the database", ex);
    }
  }

  /**
   * This method uncompress the work packages in work breakdown structure
   * @param workBreakdownStructure: work break down structure with with compressed work packages
   */
  // TODO: Call WorkPackageRecorder instead to minimize duplicate code
  protected void uncompressWorkPackages(WorkBreakdownStructure workBreakdownStructure) {
    Optional<List<WorkPackage>> compressedWorkPackages = Optional.ofNullable(workBreakdownStructure.getWorkPackages());
    if (compressedWorkPackages.isPresent()) {
      List<WorkPackage> uncompressedWorkPackages = new LinkedList<>();
      compressedWorkPackages.get().forEach(
            cwp -> IntStream.rangeClosed(1, cwp.getRequestedCount())
                  .forEach(i -> uncompressedWorkPackages
                        .add(new WorkPackage.WorkPackageBuilder(cwp.getWorkBreakdownStructure(),
                              cwp.getName(), cwp.getVersion(), INITIAL_STATUS, cwp.getCode()).build())));
      workBreakdownStructure.setWorkPackages(uncompressedWorkPackages);
    }
  }

  /**
   * Verify that the current project status allows to add work breakdown structures
   * @param projectStatus: Current status of the project
   */
  private void validateProjectStatus(final String projectStatus) {
    if (!StatusEnum.PLAN_SUBMITTED.getStatus().equals(projectStatus)
          && !(StatusEnum.PREPARATION.getStatus().equals(projectStatus))) {
      throw new BadRequestException(
            "You are not allowed to add work breakdown structure to a project with status " + projectStatus);
    }
  }

  /**
   * This method updates a work breakdown structure
   * @param projectId: Id of the project which holds the work breakdown structure
   * @param wbsId: wanted work breakdown structure Id to gets updated
   * @param workBreakdownStructure: the updates which should be implemented
   */
  public void updateWorkBreakdownStructure(final Integer projectId, final Integer wbsId,
        final WorkBreakdownStructure workBreakdownStructure) {
    //TODO: Error handling
    if (workBreakdownStructure.getId() != null && !workBreakdownStructure.getId().equals(wbsId)) {
      throw new BadRequestException(
            "Work breakdown structure id provided in the request path is " + wbsId
                  + " which doesn't match with id in the request body: " +
                  workBreakdownStructure.getId());
    }

    WorkBreakdownStructure workBreakdownStructureToBeUpdated = workBreakdownStructureRetriever
          .getWorkBreakdownStructure(projectId, wbsId);

    wbsChangeActionFactory
          .getChangeActionsToPerform(
                workBreakdownStructureToBeUpdated, workBreakdownStructure,
                projectRepository.findStatusById(projectId))
          .forEach(ChangeAction::execute);
  }

  /**
   * This method gets the start and due date of a work breakdown structure and propagates them
   * to the all work packages of it
   * @param projectId: Id of the project which holds the work breakdown structure
   * @param wbsId: Id of the work breakdown structure which its start and due date will be propagated
   */
  public void propagateStartDueDates(final Integer projectId, final Integer wbsId) {
    // IMPORTANT TODO: This method shows that when we fetch a WBS, the WPS of it is getting fetched also!
    //                 This might cause performance issues in other parts

    //TODO: add error handling in case of database access issue

    //TODO: first fetch the project to see if the user has the right permission to see it and check the status of it
    //      CPM can propagate dates if the project is in Preparation or Plan Submitted status
    //      SPM can propagate dates if the project is in Planned status

    final WorkBreakdownStructure workBreakdownStructure = workBreakdownStructureRetriever
          .getWorkBreakdownStructure(projectId, wbsId);

    final Date newStartDate = workBreakdownStructure.getStartDate();
    final Date newDueDate = workBreakdownStructure.getDueDate();

    List<WorkPackage> workPackages = workBreakdownStructure.getWorkPackages();
    if (!CollectionUtils.isEmpty(workPackages)) {
      workPackages.forEach(wp -> {
        wp.setStartDate(newStartDate);
        wp.setDueDate(newDueDate);
      });
      workPackageRepository.save(workPackages);
    }
  }

  /**
   * This method gets the standard cost model values from a work breakdown structure and propagates them
   * to the all work packages of it
   * @param projectId: Id of the project which holds the work breakdown structure
   * @param wbsId: Id of the work breakdown structure which its standard cost models will be propagated
   */
  public void propagateStandardCostModelValues(final Integer projectId, final Integer wbsId) {

    // IMPORTANT TODO: This method shows that when we fetch a WBS, the WPS of it is getting fetched also!
    //                 This might cause performance issues in other parts

    //TODO: add error handling in case of database access issue

    //TODO: first fetch the project to see if the user has the right permission to see it and check the status of it
    //      CPM can propagate SCM values if the project is in Preparation or Plan Submitted status

    final WorkBreakdownStructure workBreakdownStructure = workBreakdownStructureRetriever
          .getWorkBreakdownStructure(projectId, wbsId);

    final String purchaseOrder = workBreakdownStructure.getPurchaseOrder();
    final String networkActivity = workBreakdownStructure.getNetworkActivity();
    final String operationalActivity = workBreakdownStructure.getOperationalActivity();

    List<WorkPackage> workPackages = workBreakdownStructure.getWorkPackages();
    if (!CollectionUtils.isEmpty(workPackages)) {
      workPackages.forEach(wp -> {
        wp.setPurchaseOrder(purchaseOrder);
        wp.setNetworkActivity(networkActivity);
        wp.setOperationalActivity(operationalActivity);
        wp.setServiceOrder(null);
      });
      workPackageRepository.save(workPackages);
    }
  }

  /**
   * This method clones a work breakdown structure with its work packages with the wanted amount
   * @param projectId: Id of the project which holds the work breakdown structure
   * @param wbsId: Id o the work package which will be cloned
   * @param amount: wanted number of clones
   * @return list of cloned work breakdown structures
   */
  public List<WorkBreakdownStructure> cloneWorkBreakdownStructure(final Integer projectId, final Integer wbsId,
        final Integer amount) {

    // IMPORTANT TODO: This method shows that when we fetch a WBS, the WPS of it is getting fetched also!
    //                 This might cause performance issues in other parts

    //TODO: add error handling in case of database access issue

    //TODO: first fetch the project to see if the user has the right permission to see it and check the status of it
    //      CPM can clone a WBS inside of a project with status of Preparation , Plan Submitted or CR Preparation
    if (amount > WBS_CLONE_LIMIT) {
      throw new BadRequestException("Exceeded limitation clone amount which is " + WBS_CLONE_LIMIT);
    }
    // get the original WBS
    final WorkBreakdownStructure originalWorkBreakdownStructure = workBreakdownStructureRetriever
          .getWorkBreakdownStructure(projectId, wbsId);

    List<WorkBreakdownStructure> clonedWorkBreakdownStructures = new LinkedList<>();
    IntStream.rangeClosed(1, amount)
          .forEach(i -> {
            // deep copy of the work breakdown structure
            WorkBreakdownStructure clonedWorkBreakdownStructure = new WorkBreakdownStructure.WorkBreakdownStructureBuilder(
                  originalWorkBreakdownStructure.getProject(),
                  originalWorkBreakdownStructure.getName() + "_" + i)
                        .operationalActivity(originalWorkBreakdownStructure.getOperationalActivity())
                        .networkActivity(originalWorkBreakdownStructure.getNetworkActivity())
                        .purchaseOrder(originalWorkBreakdownStructure.getPurchaseOrder())
                        .technicalInput(originalWorkBreakdownStructure.getTechnicalInput())
                        .technicalOutput(originalWorkBreakdownStructure.getTechnicalOutput())
                        .site(originalWorkBreakdownStructure.getSite())
                        .comments(originalWorkBreakdownStructure.getComments())
                        .dueDate(originalWorkBreakdownStructure.getDueDate())
                        .startDate(originalWorkBreakdownStructure.getStartDate())
                        .subNetwork(originalWorkBreakdownStructure.getSubNetwork())
                        .label(originalWorkBreakdownStructure.getLabel()).status(INITIAL_STATUS).build();
            // deep copy of work packages
            List<WorkPackage> clonedWorkPackages = cloneWorkPackages(clonedWorkBreakdownStructure,
                  originalWorkBreakdownStructure);
            clonedWorkBreakdownStructure.setWorkPackages(clonedWorkPackages);
            clonedWorkBreakdownStructures.add(clonedWorkBreakdownStructure);
          });
    return workBreakdownStructureRepository.save(clonedWorkBreakdownStructures);
  }

  /**
   * This method deep copies the work packages of a work breakdown structure
   * @param originalWBS, work breakdown which holds the work packages
   * @return list of cloned work packages
   */
  private List<WorkPackage> cloneWorkPackages(final WorkBreakdownStructure clonedWorkBreakdownStructure,
        final WorkBreakdownStructure originalWorkBreakdownStructure) {
    final List<WorkPackage> originalWorkPackages = originalWorkBreakdownStructure.getWorkPackages();
    // deep copy of work packages
    return originalWorkPackages.stream()
          .map(orgWP -> new WorkPackage.WorkPackageBuilder(clonedWorkBreakdownStructure, orgWP.getName(),
                orgWP.getVersion(), INITIAL_STATUS, orgWP.getCode())
                      .comments(orgWP.getComments()).dueDate(orgWP.getDueDate())
                      .networkActivity(orgWP.getNetworkActivity())
                      .operationalActivity(orgWP.getOperationalActivity())
                      .purchaseOrder(orgWP.getPurchaseOrder())
                      .startDate(orgWP.getStartDate()).technicalInput(orgWP.getTechnicalInput())
                      .technicalOutput(orgWP.getTechnicalOutput()).build())
          .collect(Collectors.toList());
  }
}
