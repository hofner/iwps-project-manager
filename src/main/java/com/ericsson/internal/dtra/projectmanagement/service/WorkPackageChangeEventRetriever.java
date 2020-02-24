package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkPackageStatusEventRepository;

@Service
public class WorkPackageChangeEventRetriever {

  @Autowired
  private WorkPackageStatusEventRepository workPackageStatusEventRepository;

  @Autowired
  private WorkPackageFieldChangeEventRepository workPackageFieldChangeEventRepository;

  /**
   * Get the list of event for a work package
   * @param workPackageId the work package id
   * @return list of work package event for the requested work package
   */
  public List<ChangeEvent> getWorkPackageStatusEvents(final Integer workPackageId) {
    //TODO: Paging strategy
    //TODO: Logging
    List<ChangeEvent> changeEvents = new ArrayList<>();
    changeEvents.addAll(workPackageStatusEventRepository.findByWorkPackageId(workPackageId));
    changeEvents.addAll(workPackageFieldChangeEventRepository.findByWorkPackageId(workPackageId));
    changeEvents.sort((leftEvent, rightEvent) -> rightEvent.getTriggeredAt().compareTo(leftEvent.getTriggeredAt()));
    return changeEvents;
  }
}
