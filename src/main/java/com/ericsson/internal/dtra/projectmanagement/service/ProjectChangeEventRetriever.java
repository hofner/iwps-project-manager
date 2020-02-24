package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.ProjectStatusEventRepository;

@Service
public class ProjectChangeEventRetriever {

  @Autowired
  private ProjectStatusEventRepository projectStatusEventRepository;

  @Autowired
  private ProjectFieldChangeEventRepository projectFieldChangeEventRepository;

  /**
   * Get a project change events
   * @param projectId the unique project identifier
   * @return the change events that occurred for the project
   */
  public List<ChangeEvent> getProjectChangeEvents(final Integer projectId) {
    //TODO: Paging strategy
    //TODO: Logging
    List<ChangeEvent> changeEvents = new ArrayList<>();
    changeEvents.addAll(projectStatusEventRepository.findByProjectId(projectId));
    changeEvents.addAll(projectFieldChangeEventRepository.findByProjectId(projectId));
    changeEvents.sort((leftEvent, rightEvent) -> rightEvent.getTriggeredAt().compareTo(leftEvent.getTriggeredAt()));
    return changeEvents;
  }
}
