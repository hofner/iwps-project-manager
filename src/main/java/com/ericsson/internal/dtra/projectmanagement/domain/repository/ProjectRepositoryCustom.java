package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.dto.ProjectStatusSummary;
import com.querydsl.core.types.Predicate;

@Repository
public interface ProjectRepositoryCustom {

  /**
   * Get the count per project status
   * @return a list of Project status summary
   */
  public List<ProjectStatusSummary> getProjectStatusSummaries();

  /**
   * Get the list of project based on contextual permission
   * @param projectFilter All filters sent with the request
   * @return a list of Projects
   */
  public List<Project> getProjectsWithPermission(Predicate projectFilter);

}
