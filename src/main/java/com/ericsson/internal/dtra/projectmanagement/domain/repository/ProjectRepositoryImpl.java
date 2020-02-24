package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ericsson.internal.dtra.projectmanagement.dto.ProjectStatusSummary;
import com.ericsson.internal.dtra.projectmanagement.dto.ProjectStatusSummary.ProjectStatusSummaryRowMapper;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.QProject;
import com.ericsson.internal.dtra.projectmanagement.permissions.ResourcePermissionQueryGenerator;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

  private static final ProjectStatusSummaryRowMapper PROJECT_STATUS_SUMMARY_ROW_MAPPER = new ProjectStatusSummaryRowMapper();

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @PersistenceContext
  private EntityManager em;

  /**
   * Get the count per project status
   * @return a list of Project status summary
   */
  @Override
  public List<ProjectStatusSummary> getProjectStatusSummaries() {
    return jdbcTemplate.query(
          "SELECT status, count(*) FROM Project group by status",
          PROJECT_STATUS_SUMMARY_ROW_MAPPER);
  }

  @Override
  public List getProjectsWithPermission(Predicate projectFilter) {
    return buildProjectQueryWithPermission(projectFilter).fetch();
  }

  private JPAQuery<?> buildProjectQueryWithPermission(Predicate projectFilter) {
    JPAQuery<?> query = new JPAQuery<Project>(em);

    final QProject project = QProject.project;
    query.from(project);
    ResourcePermissionQueryGenerator queryGenerator = new ResourcePermissionQueryGenerator(query);

    queryGenerator.generateSearchQueryWithPermissions(projectFilter);

    return query;
  }
}
