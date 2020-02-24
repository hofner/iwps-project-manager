package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.ericsson.internal.csdp.authorization.utils.CSDPPermissionUtils;

import com.ericsson.internal.dtra.projectmanagement.authorization.DTRAPermission;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.AssistingCustomerProjectManager;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.AssistingServiceProjectManager;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.QProject;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.serializer.PrimeNgProjectTreeSerializer;
import com.ericsson.internal.dtra.projectmanagement.dto.ProjectStatusSummary;
import com.ericsson.internal.dtra.projectmanagement.filter.ProjectFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Service
public class ProjectRetriever {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectRetriever.class);

  private ProjectRepository projectRepository;

  private final ObjectMapper projectMapper = new ObjectMapper();
  private final SimpleModule simpleProjectModule = new SimpleModule();

  @Autowired
  public ProjectRetriever(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
    simpleProjectModule.addSerializer(Project.class, new PrimeNgProjectTreeSerializer());
    projectMapper.registerModule(simpleProjectModule);
  }

  /**
   * Get all projects the user is allowed to see
   * @param projectFilter the filters
   * @return All projects if the user is allowed, otherwise only the projects where the user is an assisting resource (CPM or SPM)
   */
  public List<Project> getProjects(final ProjectFilter projectFilter) {
    List<Project> projects = projectRepository.getProjectsWithPermission(generatePredicate(projectFilter));
    if (CSDPPermissionUtils.userHasFunctionPermission(DTRAPermission.PROJECT_VIEW_ALL)) {
      return projects;
    } else {
      return projects.stream().filter(project ->
            isUserAssistingResource(CSDPPermissionUtils.getUsername(), project)
            ).collect(Collectors.toList());
    }
  }

  /**
   * Get the project status summary. The summary is composed of the
   * number of project in each status
   * @return a list of project status summary
   */
  //TODO: Should filter based on the user permission for the projects he is allowed to view
  public List<ProjectStatusSummary> getProjectStatusSummaries() {
    //TODO: Add logging
    return projectRepository.getProjectStatusSummaries();
  }

  /**
   * Get a project based on it's ID
   * @param id the unique project identifier
   * @param primeNgPrint return a PrimeNG formatted JSON or not
   * @return a project
   */
  public Object getProject(final Integer id, final boolean primeNgPrint) {
    final Project project = projectRepository.findOne(id);

    //TODO: Handle case where project doesn't exist

    if (primeNgPrint) {
      try {
        LOGGER.info("Trying to serialize the project with ID {} with PrimeNG printing", id);
        return projectMapper.writeValueAsString(project);
      } catch (JsonProcessingException exception) {
        //TODO: Handle the case where the project cannot be serialized
        LOGGER.error(
              "Could not serialize the project into a PrimeNG JSON String. Falling back to the default project",
              exception);
      }
    }

    LOGGER.info("Returning the project with ID {} with normal printing", id);
    return project;
  }

  /**
   * Generate a predicate based on the provided project filter
   * @param filter the project filter information
   * @return a predicate matching the search scenario with the filter
   */
  public Predicate generatePredicate(final ProjectFilter filter) {

    //TODO: Add logging

    final QProject project = QProject.project;
    final BooleanBuilder predicate = new BooleanBuilder();

    // Get an optional out of the project filter
    Optional<String> optionalName = Optional.ofNullable(filter.getName());
    Optional<String> optionalCustomer = Optional.ofNullable(filter.getCustomer());
    Optional<String> optionalCountry = Optional.ofNullable(filter.getCountry().getName());
    Optional<String> optionalTimezone = Optional.ofNullable(filter.getCountry().getTimezone());
    Optional<String> optionalProductArea = Optional.ofNullable(filter.getProductArea().getName());
    Optional<String> optionalStatus = Optional.ofNullable(filter.getStatus());
    Optional<Integer> optionalWbsCompletionRate = Optional.ofNullable(filter.getWorkBreakdownStructureCompletionRate());
    Optional<Integer> optionalWpCompletionRate = Optional.ofNullable(filter.getWorkPackageCompletionRate());

    // Generate the predicate
    optionalName.ifPresent(name -> predicate.and(project.name.equalsIgnoreCase(name)));
    optionalCustomer.ifPresent(customer -> predicate.and(project.customer.equalsIgnoreCase(customer)));
    optionalCountry.ifPresent(country -> predicate.and(project.country.name.equalsIgnoreCase(country)));
    optionalProductArea.ifPresent(productArea -> predicate.and(project.productArea.name.equalsIgnoreCase(productArea)));
    optionalStatus.ifPresent(status -> predicate.and(project.status.equalsIgnoreCase(status)));
    optionalWbsCompletionRate
          .ifPresent(completionRate -> predicate.and(project.workBreakdownStructureCompletionRate.eq(completionRate)));
    optionalWpCompletionRate
          .ifPresent(completionRate -> predicate.and(project.workPackageCompletionRate.eq(completionRate)));
    optionalTimezone.ifPresent(timezone -> predicate.and(project.country.timezone.equalsIgnoreCase(timezone)));

    return predicate;
  }

  private boolean isUserAssistingResource(String userSignum, Project project) {
    return userSignum.equals(project.getCustomerProjectManagerSignum()) || userSignum.equals(project.getServiceProjectManagerSignum())
          || isAssistingCustomerProjectManager(userSignum, project.getAssistingCustomerProjectManagers())
          || isAssistingServiceProjectManager(userSignum, project.getAssistingServiceProjectManagers());
  }

  private boolean isAssistingCustomerProjectManager(String userSignum, List<AssistingCustomerProjectManager> assistingCustomerProjectManagers) {
    return assistingCustomerProjectManagers.stream()
          .map(AssistingCustomerProjectManager::getSignum)
          .anyMatch(cpmSignum -> cpmSignum.equals(userSignum));
  }

  private boolean isAssistingServiceProjectManager(String userSignum, List<AssistingServiceProjectManager> assistingServiceProjectManagers) {
    return assistingServiceProjectManagers.stream()
          .map(AssistingServiceProjectManager::getSignum)
          .anyMatch(spmSignum -> spmSignum.equals(userSignum));
  }

}
