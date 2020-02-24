package com.ericsson.internal.dtra.projectmanagement.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Country;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProductArea;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectStatusEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectDetailView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.ProjectView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkBreakdownStructureView;
import com.ericsson.internal.dtra.projectmanagement.domain.view.WorkPackageView;
import com.ericsson.internal.dtra.projectmanagement.filter.ProjectFilter;
import com.ericsson.internal.dtra.projectmanagement.filter.ProjectFilter.ProjectFilterBuilder;
import com.ericsson.internal.dtra.projectmanagement.service.ProjectChangeEventRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.ProjectRecorder;
import com.ericsson.internal.dtra.projectmanagement.service.ProjectRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.ProjectStatusEventRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.WorkBreakdownStructureRecorder;
import com.ericsson.internal.dtra.projectmanagement.service.WorkBreakdownStructureRetriever;
import com.ericsson.internal.dtra.projectmanagement.service.WorkPackageRecorder;
import com.ericsson.internal.dtra.projectmanagement.service.WorkPackageRetriever;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import se.ericsson.internal.csdp.authorization.interceptor.PermissionRequired;

@RestController
@Api(tags = { "v1", "projects" })
@RequestMapping(value = { "/v1/projects" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

  @Autowired
  private ProjectRetriever projectRetriever;

  @Autowired
  private ProjectRecorder projectRecorder;

  @Autowired
  private ProjectStatusEventRetriever projectStatusEventRetriever;

  @Autowired
  private ProjectChangeEventRetriever projectChangeEventRetriever;

  @Autowired
  private WorkPackageRecorder workPackageRecorder;

  @Autowired
  private WorkPackageRetriever workPackageRetriever;

  @Autowired
  private WorkBreakdownStructureRecorder workBreakdownStructureRecorder;

  @Autowired
  private WorkBreakdownStructureRetriever workBreakdownStructureRetriever;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// GET section
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Returns a project page", response = Project[].class)
  @JsonView(ProjectView.class)
  @PermissionRequired(DTRAPermission.PROJECT_VIEW)
  public ResponseEntity<List<Project>> getProjects(
        @ApiParam(name = "name", value = "Name of the project", required = false) @RequestParam(required = false) String name,
        @ApiParam(name = "customer", value = "Customer name the project is created for", required = false) @RequestParam(required = false) String customer,
        @ApiParam(name = "country", value = "Country the project will be implemented", required = false) @RequestParam(required = false) String country,
        @ApiParam(name = "status", value = "Current status of the project", required = false) @RequestParam(required = false) String status,
        @ApiParam(name = "possibleAction", value = "Next possible action on the project", required = false) @RequestParam(required = false) String possibleAction,
        @ApiParam(name = "productArea", value = "Name of the product area the project is under", required = false) @RequestParam(required = false) String productArea,
        @ApiParam(name = "timezone", value = "Timezone under which the project is", required = false) @RequestParam(required = false) String timezone,
        @ApiParam(name = "wbsCompletionRate", value = "Completion rate for the WBS (%)", required = false) @RequestParam(required = false) Integer wbsCompletionRate,
        @ApiParam(name = "wpCompletionRate", value = "Completion rate for the WPs (%)", required = false) @RequestParam(required = false) Integer wpCompletionRate) {

    // Generate a filter for the project selection
    ProjectFilter projectFilter = new ProjectFilterBuilder()
          .withName(name)
          .withCountry(new Country(country, timezone, null))
          .withProductArea(new ProductArea(productArea, null))
          .withCustomer(customer)
          .withStatus(status)
          .withWorkBreakdownStructureCompletionRate(wbsCompletionRate)
          .withWorkPackageCompletionRate(wpCompletionRate)
          .build();

    List<Project> projects = projectRetriever.getProjects(projectFilter);

    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  @ApiOperation(value = "Get an existing project", response = Project.class)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @JsonView(ProjectDetailView.class)
  @PermissionRequired(DTRAPermission.PROJECT_DETAILS_VIEW)
  public ResponseEntity<Object> getProject(
        @ApiParam(name = "id", value = "Id of project to fetch the details") @PathVariable final Integer id,
        @ApiParam(name = "primeNgPrint", value = "Whether to return the Project as a PrimeNG tree table format", required = false) @RequestParam(required = false) boolean primeNgPrint) {
    return new ResponseEntity<>(projectRetriever.getProject(id, primeNgPrint), HttpStatus.OK);
  }

  @ApiOperation(value = "Get a project status event history", response = ProjectStatusEvent[].class)
  @RequestMapping(value = "/{id}/status_events", method = RequestMethod.GET)
  @PermissionRequired(DTRAPermission.CHANGE_EVENT_VIEW)
  public ResponseEntity<List<ProjectStatusEvent>> getProjectStatusEvents(
        @ApiParam(name = "id", value = "Id of project to fetch the status event history") @PathVariable final Integer id) {
    return new ResponseEntity<>(projectStatusEventRetriever.getProjectStatusEvents(id), HttpStatus.OK);
  }

  @ApiOperation(value = "Get a project event history", response = ChangeEvent[].class)
  @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
  @PermissionRequired(DTRAPermission.CHANGE_EVENT_VIEW)
  public ResponseEntity<List<ChangeEvent>> getProjectChangeEvents(
        @ApiParam(name = "id", value = "Id of project to fetch list of event that occured") @PathVariable final Integer id) {
    return new ResponseEntity<>(projectChangeEventRetriever.getProjectChangeEvents(id), HttpStatus.OK);
  }

  @ApiOperation(value = "Get an existing work breakdown structure for a specific project", response = WorkBreakdownStructure.class)
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}", method = RequestMethod.GET)
  @JsonView(WorkBreakdownStructureView.class)
  @PermissionRequired(DTRAPermission.PROJECT_DETAILS_VIEW)
  public ResponseEntity<WorkBreakdownStructure> getWorkBreakdownStructure(
        @ApiParam(name = "project Id", value = "Id of project to fetch the work breakdown structure for that") @PathVariable final Integer projectId,
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown structure to fetch") @PathVariable final Integer wbsId) {
    return new ResponseEntity<>(workBreakdownStructureRetriever.getWorkBreakdownStructure(projectId, wbsId),
          HttpStatus.OK);
  }

  @ApiOperation(value = "Get an existing work package for a specific work breakdown structure of a specific project", response = WorkPackage.class)
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}/work_packages/{wpId}", method = RequestMethod.GET)
  @JsonView(WorkPackageView.class)
  @PermissionRequired(DTRAPermission.PROJECT_DETAILS_VIEW)
  public ResponseEntity<WorkPackage> getWorkPackage(
        @ApiParam(name = "project Id", value = "Id of project which holds the work package") @PathVariable final Integer projectId,
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown structure which holds the work package") @PathVariable final Integer wbsId,
        @ApiParam(name = "work package Id", value = "Id of work package to fetch") @PathVariable final Integer wpId) {
    return new ResponseEntity<>(workPackageRetriever.getWorkPackage(projectId, wbsId, wpId),
          HttpStatus.OK);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// POST section
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @ApiOperation(value = "Create a new project", response = Project.class)
  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PermissionRequired(DTRAPermission.PROJECT_CREATE)
  public ResponseEntity<Project> createProject(
        @ApiParam(value = "Project to be created", required = true) @RequestBody final Project project) {
    return new ResponseEntity<>(projectRecorder.createProject(project), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Create one new work breakdown structure with its work packages", response = WorkBreakdownStructure.class)
  @RequestMapping(value = "/{projectId}/work_breakdown_structures", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PermissionRequired(DTRAPermission.WBS_CREATE)
  public ResponseEntity<WorkBreakdownStructure> createWorkBreakdownStructure(
        @ApiParam(value = "Work Breakdown Structure to be created", required = true) @RequestBody final WorkBreakdownStructure workBreakdownStructure,
        @ApiParam(value = "project id", required = true) @PathVariable final Integer projectId) {
    return new ResponseEntity<>(
          workBreakdownStructureRecorder.createWorkBreakdownStructure(projectId, workBreakdownStructure),
          HttpStatus.CREATED);
  }

  @ApiOperation(value = "Create new work packages for a specific work breakdown structure", response = WorkPackage[].class)
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}/work_packages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PermissionRequired(DTRAPermission.WP_CREATE)
  public ResponseEntity<List<WorkPackage>> createWorkPackages(
        @ApiParam(value = "work packages to be created", required = true) @RequestBody final List<WorkPackage> workPackages,
        @ApiParam(value = "project id", required = true) @PathVariable final Integer projectId,
        @ApiParam(value = "work breakdown structure id", required = true) @PathVariable final Integer wbsId) {
    return new ResponseEntity<>(workPackageRecorder.createWorkPackages(projectId, wbsId, workPackages),
          HttpStatus.CREATED);
  }

  @ApiOperation(value = "Clone an existing work breakdown structure with its work packages", response = WorkBreakdownStructure[].class)
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @PermissionRequired(DTRAPermission.WP_CREATE)
  public ResponseEntity<List<WorkBreakdownStructure>> cloneWorkBreakdownStructure(
        @ApiParam(value = "project id", required = true) @PathVariable final Integer projectId,
        @ApiParam(value = "work breakdown structure id", required = true) @PathVariable final Integer wbsId,
        @ApiParam(name = "number of clones", value = "Number of wanted work breakdown structures", required = true) @RequestParam final Integer amount) {
    return new ResponseEntity<>(
          workBreakdownStructureRecorder.cloneWorkBreakdownStructure(projectId, wbsId, amount),
          HttpStatus.CREATED);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// PUT section
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @ApiOperation(value = "Update an existing project")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PermissionRequired(DTRAPermission.PROJECT_DETAILS_EDIT)
  public void updateProject(
        @ApiParam(name = "id", value = "Id of project to be updated") @PathVariable final Integer id,
        @ApiParam(value = "project to be updated", required = true) @RequestBody Project project) {

    projectRecorder.update(id, project);
  }

  @ApiOperation(value = "Update an existing work breakdown structure")
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PermissionRequired(DTRAPermission.WBS_EDIT)
  public void updateWorkBreakdownStructure(
        @ApiParam(name = "project Id", value = "Id of project which holds the work breakdown structure") @PathVariable final Integer projectId,
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown structure to get updated") @PathVariable final Integer wbsId,
        @ApiParam(value = "Update information of a work breakdown structure", required = true) @RequestBody final WorkBreakdownStructure workBreakdownStructure) {

    workBreakdownStructureRecorder.updateWorkBreakdownStructure(projectId, wbsId, workBreakdownStructure);
  }

  @ApiOperation(value = "Update an existing work package")
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}/work_packages/{wpId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PermissionRequired(DTRAPermission.WP_EDIT)
  public void updateWorkPackage(
        @ApiParam(name = "project Id", value = "Id of project which holds the work package") @PathVariable final Integer projectId,
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown which holds the work package") @PathVariable final Integer wbsId,
        @ApiParam(name = "work package Id", value = "Id of work package to be updated") @PathVariable final Integer wpId,
        @ApiParam(value = "Update information of a work package", required = true) @RequestBody final WorkPackage workPackage) {

    workPackageRecorder.updateWorkPackage(projectId, wbsId, wpId, workPackage);
  }

  @ApiOperation(value = "propagate start and due dates")
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}/completion_dates", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PermissionRequired(DTRAPermission.WP_EDIT)
  public void propagateStartDueDates(
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown structure") @PathVariable final Integer wbsId,
        @ApiParam(name = "projectId", value = "Id of project which holds the work breakdown structure") @PathVariable final Integer projectId) {
    workBreakdownStructureRecorder.propagateStartDueDates(projectId, wbsId);
  }

  @ApiOperation(value = "propagate standard cost model values")
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsId}/standard_costmodel_values", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PermissionRequired(DTRAPermission.WP_EDIT)
  public void propagateStandardCostmodelValues(
        @ApiParam(name = "work breakdown structure Id", value = "Id of work breakdown structure") @PathVariable final Integer wbsId,
        @ApiParam(name = "projectId", value = "Id of project which holds the work breakdown structure") @PathVariable final Integer projectId) {
    workBreakdownStructureRecorder.propagateStandardCostModelValues(projectId, wbsId);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /// DELETE section
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @ApiOperation(value = "Delete a list of work breakdown structures by ids")
  @RequestMapping(value = "/{projectId}/work_breakdown_structures/{wbsIds}", method = RequestMethod.DELETE)
  @PermissionRequired(DTRAPermission.WBS_DELETE)
  public ResponseEntity<String> deleteWorkBreakdownStructures(
        @ApiParam(name = "projectId", value = "Id of project which holds the work breakdown structures") @PathVariable final Integer projectId,
        @ApiParam(name = "wbsIds", value = "List of Work Breakdown Structure ids to be deleted") @PathVariable final Integer[] wbsIds) {
    workBreakdownStructureRetriever.deleteWorkBreakdownStructures(projectId, wbsIds);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "Delete a list of work package by ids")
  @RequestMapping(value = "/{projectId}/work_packages/{wpIds}", method = RequestMethod.DELETE)
  @PermissionRequired(DTRAPermission.WP_DELETE)
  public ResponseEntity<String> deleteWorkPackages(
        @ApiParam(name = "projectId", value = "Id of project which holds the work packages") @PathVariable final Integer projectId,
        @ApiParam(name = "wpIds", value = "List of Work Package ids to be deleted") @PathVariable final Integer[] wpIds) {
    workPackageRetriever.deleteWorkPackages(projectId, wpIds);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
