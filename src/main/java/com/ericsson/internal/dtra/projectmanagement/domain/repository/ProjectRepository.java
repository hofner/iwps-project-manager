package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;

@Repository
public interface ProjectRepository extends
      JpaRepository<Project, Integer>,
      QueryDslPredicateExecutor<Project>,
      ProjectRepositoryCustom {

  public Project findById(Integer projectId);

  /**
   * Get the status of a project based on its ID
   * @param id the project id
   * @return return all the time zone names
   */
  @Query("SELECT project.status FROM Project project WHERE project.id = :id")
  public String findStatusById(@Param("id") Integer id);
}
