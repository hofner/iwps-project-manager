package com.ericsson.internal.dtra.projectmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.ChangeEvent;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureFieldChangeEventRepository;
import com.ericsson.internal.dtra.projectmanagement.domain.repository.WorkBreakdownStructureStatusEventRepository;

@Service
public class WorkBreakdownStructureChangeEventRetriever {

  @Autowired
  private WorkBreakdownStructureStatusEventRepository wbsStatusEventRepository;

  @Autowired
  private WorkBreakdownStructureFieldChangeEventRepository wbsFieldChangeEventRepository;

  /**
   * Get the list of change event for a work breakdown structure
   * @param workBreakdownStructureId the work breakdown structure id
   * @return list of project event for the requested work breakdown structure
   */
  public List<ChangeEvent> getWorkBreakdownStructureStatusEvents(final Integer workBreakdownStructureId) {
    //TODO: Paging strategy
    //TODO: Logging
    List<ChangeEvent> changeEvents = new ArrayList<>();
    changeEvents.addAll(wbsStatusEventRepository.findByWorkBreakdownStructureId(workBreakdownStructureId));
    changeEvents.addAll(wbsFieldChangeEventRepository.findByWorkBreakdownStructureId(workBreakdownStructureId));
    changeEvents.sort((leftEvent, rightEvent) -> rightEvent.getTriggeredAt().compareTo(leftEvent.getTriggeredAt()));
    return changeEvents;
  }
}
