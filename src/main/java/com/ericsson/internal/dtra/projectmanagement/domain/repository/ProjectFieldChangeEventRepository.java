package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectFieldChangeEvent;

@Repository
public interface ProjectFieldChangeEventRepository extends JpaRepository<ProjectFieldChangeEvent, Integer> {

  /**
   * Get the list of field change event for a project
   * @param projectId the project id
   * @return list of project field change event for the requested project
   */
  List<ProjectFieldChangeEvent> findByProjectId(Integer projectId);
}
