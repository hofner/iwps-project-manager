package com.ericsson.internal.dtra.projectmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectStatusEvent;

@Repository
public interface ProjectStatusEventRepository extends JpaRepository<ProjectStatusEvent, Integer> {

  /**
   * Get the list of status event for a project
   * @param projectId the project id
   * @return list of project status event for the requested project
   */
  List<ProjectStatusEvent> findByProjectId(Integer projectId);
}
