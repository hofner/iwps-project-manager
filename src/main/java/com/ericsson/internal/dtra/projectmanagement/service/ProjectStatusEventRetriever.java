package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ProjectStatusEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectStatusEventRepository;

@Service
public class ProjectStatusEventRetriever {

  @Autowired
  private ProjectStatusEventRepository projectStatusEventRepository;

  /**
   * Get a project status events
   * @param projectId the unique project identifier
   * @return the status events of the project
   */
  public List<ProjectStatusEvent> getProjectStatusEvents(final Integer projectId) {
    //TODO: Paging strategy
    //TODO: Logging
    return projectStatusEventRepository.findByProjectId(projectId);
  }
}
